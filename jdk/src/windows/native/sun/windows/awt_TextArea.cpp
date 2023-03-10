/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

#include "awt_Toolkit.h"
#include "awt_TextArea.h"
#include "awt_TextComponent.h"
#include "awt_Canvas.h"
#include "awt_Window.h"
#include "awt_Frame.h"

/* IMPORTANT! Read the README.JNI file for notes on JNI converted AWT code.
 */

/***********************************************************************/
// Struct for _ReplaceText() method
struct ReplaceTextStruct {
  jobject textComponent;
  jstring text;
  int start, end;
};

/************************************************************************
 * AwtTextArea fields
 */

jfieldID AwtTextArea::scrollbarVisibilityID;

WNDPROC AwtTextArea::sm_pDefWindowProc = NULL;

/************************************************************************
 * AwtTextArea methods
 */

AwtTextArea::AwtTextArea() {
    m_bIgnoreEnChange = FALSE;
    m_bCanUndo        = FALSE;
    m_hEditCtrl       = NULL;
    m_lHDeltaAccum    = 0;
    m_lVDeltaAccum    = 0;
}

AwtTextArea::~AwtTextArea()
{
}

void AwtTextArea::Dispose()
{
    if (m_hEditCtrl != NULL) {
        VERIFY(::DestroyWindow(m_hEditCtrl));
        m_hEditCtrl = NULL;
    }
    AwtTextComponent::Dispose();
}

/* Create a new AwtTextArea object and window.   */
AwtTextArea* AwtTextArea::Create(jobject peer, jobject parent)
{
    return (AwtTextArea*) AwtTextComponent::Create(peer, parent, true);
}

void AwtTextArea::EditSetSel(CHARRANGE &cr) {
    // Fix for 5003402: added restoring/hiding selection to enable automatic scrolling
    SendMessage(EM_HIDESELECTION, FALSE, TRUE);
    SendMessage(EM_EXSETSEL, 0, reinterpret_cast<LPARAM>(&cr));
    SendMessage(EM_HIDESELECTION, TRUE, TRUE);
    // 6417581: force expected drawing
    if (IS_WINVISTA && cr.cpMin == cr.cpMax) {
        ::InvalidateRect(GetHWnd(), NULL, TRUE);
    }
}

void AwtTextArea::EditGetSel(CHARRANGE &cr) {
    SendMessage(EM_EXGETSEL, 0, reinterpret_cast<LPARAM>(&cr));
}

/* Count how many '\n's are there in jStr */
size_t AwtTextArea::CountNewLines(JNIEnv *env, jstring jStr, size_t maxlen)
{
    size_t nNewlines = 0;

    if (jStr == NULL) {
        return nNewlines;
    }
    /*
     * Fix for BugTraq Id 4260109.
     * Don't use TO_WSTRING since it allocates memory on the stack
     * causing stack overflow when the text is very long.
     */
    size_t length = env->GetStringLength(jStr) + 1;
    WCHAR *string = new WCHAR[length];
    env->GetStringRegion(jStr, 0, static_cast<jsize>(length - 1), reinterpret_cast<jchar*>(string));
    string[length-1] = '\0';
    for (size_t i = 0; i < maxlen && i < length - 1; i++) {
        if (string[i] == L'\n') {
            nNewlines++;
        }
    }
    delete[] string;
    return nNewlines;
}

BOOL AwtTextArea::InheritsNativeMouseWheelBehavior() {return true;}


LRESULT
AwtTextArea::WindowProc(UINT message, WPARAM wParam, LPARAM lParam) {

    LRESULT retValue = 0;
    MsgRouting mr = mrDoDefault;

    switch (message) {
    case EM_SETCHARFORMAT:
    case WM_SETFONT:
        SetIgnoreEnChange(TRUE);
        break;
    }

    retValue = AwtTextComponent::WindowProc(message, wParam, lParam);

    switch (message) {
    case EM_SETCHARFORMAT:
    case WM_SETFONT:
        SetIgnoreEnChange(FALSE);
        break;
    }

    return retValue;
}

/*
 * This routine is a window procedure for the subclass of the standard edit control
 * used to generate context menu. RichEdit controls don't have built-in context menu.
 * To implement this functionality we have to create an invisible edit control and
 * forward WM_CONTEXTMENU messages from a RichEdit control to this helper edit control.
 * While the edit control context menu is active we intercept the message generated in
 * response to particular item selection and forward it back to the RichEdit control.
 * (See AwtTextArea::WmContextMenu for more details).
 */
LRESULT
AwtTextArea::EditProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam) {

    static BOOL bContextMenuActive = FALSE;

    LRESULT retValue = 0;
    MsgRouting mr = mrDoDefault;

    DASSERT(::IsWindow(::GetParent(hWnd)));

    switch (message) {
    case WM_UNDO:
    case WM_CUT:
    case WM_COPY:
    case WM_PASTE:
    case WM_CLEAR:
    case EM_SETSEL:
        if (bContextMenuActive) {
            ::SendMessage(::GetParent(hWnd), message, wParam, lParam);
            mr = mrConsume;
        }
        break;
    case WM_CONTEXTMENU:
        bContextMenuActive = TRUE;
        break;
    }

    if (mr == mrDoDefault) {
        DASSERT(sm_pDefWindowProc != NULL);
        retValue = ::CallWindowProc(sm_pDefWindowProc,
                                    hWnd, message, wParam, lParam);
    }

    if (message == WM_CONTEXTMENU) {
        bContextMenuActive = FALSE;
    }

    return retValue;
}

MsgRouting
AwtTextArea::WmContextMenu(HWND hCtrl, UINT xPos, UINT yPos) {
    /* Use the system provided edit control class to generate context menu. */
    if (m_hEditCtrl == NULL) {
        DWORD dwStyle = WS_CHILD;
        DWORD dwExStyle = 0;
        m_hEditCtrl = ::CreateWindowEx(dwExStyle,
                                        L"EDIT",
                                        L"TEXT",
                                        dwStyle,
                                        0, 0, 0, 0,
                                        GetHWnd(),
                                        reinterpret_cast<HMENU>(
                                         static_cast<INT_PTR>(
                                             CreateControlID())),
                                        AwtToolkit::GetInstance().GetModuleHandle(),
                                        NULL);
        DASSERT(m_hEditCtrl != NULL);
        if (sm_pDefWindowProc == NULL) {
            sm_pDefWindowProc = (WNDPROC)::GetWindowLongPtr(m_hEditCtrl,
                                                         GWLP_WNDPROC);
        }
        ::SetLastError(0);
        INT_PTR ret = ::SetWindowLongPtr(m_hEditCtrl, GWLP_WNDPROC,
                                   (INT_PTR)AwtTextArea::EditProc);
        DASSERT(ret != 0 || ::GetLastError() == 0);
    }

    /*
     * Tricks on the edit control to ensure that its context menu has
     * the correct set of enabled items according to the RichEdit state.
     */
    ::SetWindowText(m_hEditCtrl, TEXT("TEXT"));

    if (m_bCanUndo == TRUE && SendMessage(EM_CANUNDO)) {
        /* Enable 'Undo' item. */
        ::SendMessage(m_hEditCtrl, WM_CHAR, 'A', 0);
    }

    {
        /*
         * Initial selection for the edit control - (0,1).
         * This enables 'Cut', 'Copy' and 'Delete' and 'Select All'.
         */
        INT nStart = 0;
        INT nEnd = 1;
        if (SendMessage(EM_SELECTIONTYPE) == SEL_EMPTY) {
            /*
             * RichEdit selection is empty - clear selection of the edit control.
             * This disables 'Cut', 'Copy' and 'Delete'.
             */
            nStart = -1;
            nEnd = 0;
        } else {

            CHARRANGE cr;
            EditGetSel(cr);
            /* Check if all the text is selected. */
            if (cr.cpMin == 0) {

                int len = ::GetWindowTextLength(GetHWnd());
                if (cr.cpMin == 0 && cr.cpMax >= len) {
                    /*
                     * All the text is selected in RichEdit - select all the
                     * text in the edit control. This disables 'Select All'.
                     */
                    nStart = 0;
                    nEnd = -1;
                }
            }
        }
        ::SendMessage(m_hEditCtrl, EM_SETSEL, (WPARAM)nStart, (LPARAM)nEnd);
    }

    /* Disable 'Paste' item if the RichEdit control is read-only. */
    ::SendMessage(m_hEditCtrl, EM_SETREADONLY,
                  GetStyle() & ES_READONLY ? TRUE : FALSE, 0);

    POINT p;
    p.x = xPos;
    p.y = yPos;

    /*
     * If the context menu is requested with SHIFT+F10 or VK_APPS key,
     * we position its top left corner to the center of the RichEdit
     * client rect.
     */
    if (p.x == -1 && p.y == -1) {
        RECT r;
        VERIFY(::GetClientRect(GetHWnd(), &r));
        p.x = (r.left + r.right) / 2;
        p.y = (r.top + r.bottom) / 2;
        VERIFY(::ClientToScreen(GetHWnd(), &p));
    }

    // The context menu steals focus from the proxy.
    // So, set the focus-restore flag up.
    SetRestoreFocus(TRUE);
    ::SendMessage(m_hEditCtrl, WM_CONTEXTMENU, (WPARAM)m_hEditCtrl, MAKELPARAM(p.x, p.y));
    SetRestoreFocus(FALSE);

    return mrConsume;
}

MsgRouting
AwtTextArea::WmNcHitTest(UINT x, UINT y, LRESULT& retVal)
{
    if (::IsWindow(AwtWindow::GetModalBlocker(AwtComponent::GetTopLevelParentForWindow(GetHWnd())))) {
        retVal = HTCLIENT;
        return mrConsume;
    }
    return AwtTextComponent::WmNcHitTest(x, y, retVal);
}


MsgRouting
AwtTextArea::WmNotify(UINT notifyCode)
{
    if (notifyCode == EN_CHANGE) {
        /*
         * Ignore notifications if the text hasn't been changed.
         * EN_CHANGE sent on character formatting changes as well.
         */
        if (m_bIgnoreEnChange == FALSE) {
            m_bCanUndo = TRUE;
            DoCallback("valueChanged", "()V");
        } else {
            m_bCanUndo = FALSE;
        }
    }
    return mrDoDefault;
}

MsgRouting
AwtTextArea::HandleEvent(MSG *msg, BOOL synthetic)
{
    MsgRouting returnVal;
    /*
     * RichEdit 1.0 control starts internal message loop if the
     * left mouse button is pressed while the cursor is not over
     * the current selection or the current selection is empty.
     * Because of this we don't receive WM_MOUSEMOVE messages
     * while the left mouse button is pressed. To work around
     * this behavior we process the relevant mouse messages
     * by ourselves.
     * By consuming WM_MOUSEMOVE messages we also don't give
     * the RichEdit control a chance to recognize a drag gesture
     * and initiate its own drag-n-drop operation.
     *
     * The workaround also allows us to implement synthetic focus mechanism.
     *
     */
    if (IsFocusingMouseMessage(msg)) {
        CHARRANGE cr;

        LONG lCurPos = EditGetCharFromPos(msg->pt);

        EditGetSel(cr);
        /*
         * NOTE: Plain EDIT control always clears selection on mouse
         * button press. We are clearing the current selection only if
         * the mouse pointer is not over the selected region.
         * In this case we sacrifice backward compatibility
         * to allow dnd of the current selection.
         */
        if (lCurPos < cr.cpMin || cr.cpMax <= lCurPos) {
            if (msg->message == WM_LBUTTONDBLCLK) {
                SetStartSelectionPos(static_cast<LONG>(SendMessage(
                    EM_FINDWORDBREAK, WB_MOVEWORDLEFT, lCurPos)));
                SetEndSelectionPos(static_cast<LONG>(SendMessage(
                    EM_FINDWORDBREAK, WB_MOVEWORDRIGHT, lCurPos)));
            } else {
                SetStartSelectionPos(lCurPos);
                SetEndSelectionPos(lCurPos);
            }
            cr.cpMin = GetStartSelectionPos();
            cr.cpMax = GetEndSelectionPos();
            EditSetSel(cr);
        }

        delete msg;
        return mrConsume;
    } else if (msg->message == WM_LBUTTONUP) {

        /*
         * If the left mouse button is pressed on the selected region
         * we don't clear the current selection. We clear it on button
         * release instead. This is to allow dnd of the current selection.
         */
        if (GetStartSelectionPos() == -1 && GetEndSelectionPos() == -1) {
            CHARRANGE cr;

            LONG lCurPos = EditGetCharFromPos(msg->pt);

            cr.cpMin = lCurPos;
            cr.cpMax = lCurPos;
            EditSetSel(cr);
        }

        /*
         * Cleanup the state variables when left mouse button is released.
         * These state variables are designed to reflect the selection state
         * while the left mouse button is pressed and be set to -1 otherwise.
         */
        SetStartSelectionPos(-1);
        SetEndSelectionPos(-1);
        SetLastSelectionPos(-1);

        delete msg;
        return mrConsume;
    } else if (msg->message == WM_MOUSEMOVE && (msg->wParam & MK_LBUTTON)) {

        /*
         * We consume WM_MOUSEMOVE while the left mouse button is pressed,
         * so we have to simulate autoscrolling when mouse is moved outside
         * of the client area.
         */
        POINT p;
        RECT r;
        BOOL bScrollLeft = FALSE;
        BOOL bScrollRight = FALSE;
        BOOL bScrollUp = FALSE;
        BOOL bScrollDown = FALSE;

        p.x = msg->pt.x;
        p.y = msg->pt.y;
        VERIFY(::GetClientRect(GetHWnd(), &r));

        if (p.x < 0) {
            bScrollLeft = TRUE;
            p.x = 0;
        } else if (p.x > r.right) {
            bScrollRight = TRUE;
            p.x = r.right - 1;
        }
        if (p.y < 0) {
            bScrollUp = TRUE;
            p.y = 0;
        } else if (p.y > r.bottom) {
            bScrollDown = TRUE;
            p.y = r.bottom - 1;
        }

        LONG lCurPos = EditGetCharFromPos(p);

        if (GetStartSelectionPos() != -1 &&
            GetEndSelectionPos() != -1 &&
            lCurPos != GetLastSelectionPos()) {

            CHARRANGE cr;

            SetLastSelectionPos(lCurPos);

            cr.cpMin = GetStartSelectionPos();
            cr.cpMax = GetLastSelectionPos();

            EditSetSel(cr);
        }

        if (bScrollLeft == TRUE || bScrollRight == TRUE) {
            SCROLLINFO si;
            memset(&si, 0, sizeof(si));
            si.cbSize = sizeof(si);
            si.fMask = SIF_PAGE | SIF_POS | SIF_RANGE;

            VERIFY(::GetScrollInfo(GetHWnd(), SB_HORZ, &si));
            if (bScrollLeft == TRUE) {
                si.nPos = si.nPos - si.nPage / 2;
                si.nPos = max(si.nMin, si.nPos);
            } else if (bScrollRight == TRUE) {
                si.nPos = si.nPos + si.nPage / 2;
                si.nPos = min(si.nPos, si.nMax);
            }
            /*
             * Okay to use 16-bit position since RichEdit control adjusts
             * its scrollbars so that their range is always 16-bit.
             */
            DASSERT(abs(si.nPos) < 0x8000);
            SendMessage(WM_HSCROLL,
                        MAKEWPARAM(SB_THUMBPOSITION, LOWORD(si.nPos)));
        }
        if (bScrollUp == TRUE) {
            SendMessage(EM_LINESCROLL, 0, -1);
        } else if (bScrollDown == TRUE) {
            SendMessage(EM_LINESCROLL, 0, 1);
        }
        delete msg;
        return mrConsume;
    } else if (msg->message == WM_RBUTTONUP ||
               (msg->message == WM_SYSKEYDOWN && msg->wParam == VK_F10 &&
                HIBYTE(::GetKeyState(VK_SHIFT)))) {
        POINT p;
        if (msg->message == WM_RBUTTONUP) {
            VERIFY(::GetCursorPos(&p));
        } else {
            p.x = -1;
            p.y = -1;
        }

        if (!::PostMessage(GetHWnd(), WM_CONTEXTMENU, (WPARAM)GetHWnd(),
                           MAKELPARAM(p.x, p.y))) {
            JNIEnv *env = (JNIEnv *)JNU_GetEnv(jvm, JNI_VERSION_1_2);
            JNU_ThrowInternalError(env, "Message not posted, native event queue may be full.");
            env->ExceptionDescribe();
            env->ExceptionClear();
        }
        delete msg;
        return mrConsume;
    } else if (msg->message == WM_MOUSEWHEEL) {
        // 4417236: If there is an old version of RichEd32.dll which
        // does not provide the mouse wheel scrolling we have to
        // interpret WM_MOUSEWHEEL as a sequence of scroll messages.
        // kdm@sparc.spb.su
        UINT platfScrollLines = 3;
        // Retrieve a number of scroll lines.
        ::SystemParametersInfo(SPI_GETWHEELSCROLLLINES, 0,
                               &platfScrollLines, 0);

        if (platfScrollLines > 0) {
            HWND hWnd = GetHWnd();
            LONG styles = ::GetWindowLong(hWnd, GWL_STYLE);

            RECT rect;
            // rect.left and rect.top are zero.
            // rect.right and rect.bottom contain the width and height
            VERIFY(::GetClientRect(hWnd, &rect));

            // calculate a number of visible lines
            TEXTMETRIC tm;
            HDC hDC = ::GetDC(hWnd);
            DASSERT(hDC != NULL);
            VERIFY(::GetTextMetrics(hDC, &tm));
            VERIFY(::ReleaseDC(hWnd, hDC));
            LONG visibleLines = rect.bottom / tm.tmHeight + 1;

            LONG lineCount = static_cast<LONG>(::SendMessage(hWnd,
                EM_GETLINECOUNT, 0, 0));
            BOOL sb_vert_disabled = (styles & WS_VSCROLL) == 0
              || (lineCount <= visibleLines);

            LONG *delta_accum = &m_lVDeltaAccum;
            UINT wm_msg = WM_VSCROLL;
            int sb_type = SB_VERT;

            if (sb_vert_disabled && (styles & WS_HSCROLL)) {
                delta_accum = &m_lHDeltaAccum;
                wm_msg = WM_HSCROLL;
                sb_type = SB_HORZ;
            }

            int delta = (short) HIWORD(msg->wParam);
            *delta_accum += delta;
            if (abs(*delta_accum) >= WHEEL_DELTA) {
                if (platfScrollLines == WHEEL_PAGESCROLL) {
                    // Synthesize a page down or a page up message.
                    ::SendMessage(hWnd, wm_msg,
                                  (delta > 0) ? SB_PAGEUP : SB_PAGEDOWN, 0);
                    *delta_accum = 0;
                } else {
                    // We provide a friendly behavior of text scrolling.
                    // During of scrolling the text can be out of the client
                    // area's boundary. Here we try to prevent this behavior.
                    SCROLLINFO si;
                    ::ZeroMemory(&si, sizeof(si));
                    si.cbSize = sizeof(SCROLLINFO);
                    si.fMask = SIF_POS | SIF_RANGE | SIF_PAGE;
                    int actualScrollLines =
                        abs((int)(platfScrollLines * (*delta_accum / WHEEL_DELTA)));
                    for (int i = 0; i < actualScrollLines; i++) {
                        if (::GetScrollInfo(hWnd, sb_type, &si)) {
                            if ((wm_msg == WM_VSCROLL)
                                && ((*delta_accum < 0
                                     && si.nPos >= (si.nMax - (int) si.nPage))
                                    || (*delta_accum > 0
                                        && si.nPos <= si.nMin))) {
                                break;
                            }
                        }
                        // Here we don't send EM_LINESCROLL or EM_SCROLL
                        // messages to rich edit because it doesn't
                        // provide horizontal scrolling.
                        // So it's only possible to scroll by 1 line
                        // at a time to prevent scrolling when the
                        // scrollbar thumb reaches its boundary position.
                        ::SendMessage(hWnd, wm_msg,
                            (*delta_accum>0) ? SB_LINEUP : SB_LINEDOWN, 0);
                    }
                    *delta_accum %= WHEEL_DELTA;
                }
            } else {
                *delta_accum = 0;
            }
        }
        delete msg;
        return mrConsume;
        // 4417236: end of fix
    }

    /*
     * Store the 'synthetic' parameter so that the WM_PASTE security check
     * happens only for synthetic events.
     */
    m_synthetic = synthetic;
    returnVal = AwtComponent::HandleEvent(msg, synthetic);
    m_synthetic = FALSE;

    return returnVal;
}


/* Fix for 4776535, 4648702
 * If width is 0 or 1 Windows hides the horizontal scroll bar even
 * if the WS_HSCROLL style is set. It is a bug in Windows.
 * As a workaround we should set an initial width to 2.
 * kdm@sparc.spb.su
 */
void AwtTextArea::Reshape(int x, int y, int w, int h)
{
    if (w < 2) {
        w = 2;
    }
    AwtTextComponent::Reshape(x, y, w, h);
}

LONG AwtTextArea::getJavaSelPos(LONG orgPos)
{
    long wlen;
    long pos = orgPos;
    long cur = 0;
    long retval = 0;
    LPTSTR wbuf;

    if ((wlen = GetTextLength()) == 0)
        return 0;
    wbuf = new TCHAR[wlen + 1];
    GetText(wbuf, wlen + 1);
    if (m_isLFonly == TRUE) {
        wlen = RemoveCR(wbuf);
    }

    while (cur < pos && cur < wlen) {
        if (wbuf[cur] == _T('\r') && wbuf[cur + 1] == _T('\n')) {
            pos++;
        }
        cur++;
        retval++;
    }
    delete[] wbuf;
    return retval;
}

LONG AwtTextArea::getWin32SelPos(LONG orgPos)
{
    if (GetTextLength() == 0)
       return 0;
    return orgPos;
}

void AwtTextArea::SetSelRange(LONG start, LONG end)
{
    CHARRANGE cr;
    cr.cpMin = getWin32SelPos(start);
    cr.cpMax = getWin32SelPos(end);
    EditSetSel(cr);
}


void AwtTextArea::_ReplaceText(void *param)
{
    JNIEnv *env = (JNIEnv *)JNU_GetEnv(jvm, JNI_VERSION_1_2);

    ReplaceTextStruct *rts = (ReplaceTextStruct *)param;

    jobject textComponent = rts->textComponent;
    jstring text = rts->text;
    jint start = rts->start;
    jint end = rts->end;

    AwtTextComponent *c = NULL;

    PDATA pData;
    JNI_CHECK_PEER_GOTO(textComponent, done);
    JNI_CHECK_NULL_GOTO(text, "null string", done);

    c = (AwtTextComponent *)pData;
    if (::IsWindow(c->GetHWnd()))
    {
      jsize length = env->GetStringLength(text) + 1;
      // Bugid 4141477 - Can't use TO_WSTRING here because it uses alloca
      // WCHAR* buffer = TO_WSTRING(text);
      TCHAR *buffer = new TCHAR[length];
      env->GetStringRegion(text, 0, length-1, reinterpret_cast<jchar*>(buffer));
      buffer[length-1] = '\0';

      c->CheckLineSeparator(buffer);
      c->RemoveCR(buffer);
      // Fix for 5003402: added restoring/hiding selection to enable automatic scrolling
      c->SendMessage(EM_HIDESELECTION, FALSE, TRUE);
      c->SendMessage(EM_SETSEL, start, end);
      c->SendMessage(EM_REPLACESEL, FALSE, (LPARAM)buffer);
      c->SendMessage(EM_HIDESELECTION, TRUE, TRUE);

      delete[] buffer;
    }

done:
    env->DeleteGlobalRef(textComponent);
    env->DeleteGlobalRef(text);

    delete rts;
}


/************************************************************************
 * TextArea native methods
 */

extern "C" {

/*
 * Class:     java_awt_TextArea
 * Method:    initIDs
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_java_awt_TextArea_initIDs(JNIEnv *env, jclass cls)
{
    TRY;

    AwtTextArea::scrollbarVisibilityID =
        env->GetFieldID(cls, "scrollbarVisibility", "I");

    DASSERT(AwtTextArea::scrollbarVisibilityID != NULL);

    CATCH_BAD_ALLOC;
}

} /* extern "C" */


/************************************************************************
 * WTextAreaPeer native methods
 */

extern "C" {

/*
 * Class:     sun_awt_windows_WTextAreaPeer
 * Method:    create
 * Signature: (Lsun/awt/windows/WComponentPeer;)V
 */
JNIEXPORT void JNICALL
Java_sun_awt_windows_WTextAreaPeer_create(JNIEnv *env, jobject self,
                                          jobject parent)
{
    TRY;

    PDATA pData;
    JNI_CHECK_PEER_RETURN(parent);
    AwtToolkit::CreateComponent(self, parent,
                                (AwtToolkit::ComponentFactory)
                                AwtTextArea::Create);
    JNI_CHECK_PEER_CREATION_RETURN(self);

    CATCH_BAD_ALLOC;
}

/*
 * Class:     sun_awt_windows_WTextAreaPeer
 * Method:    replaceRange
 * Signature: (Ljava/lang/String;II)V
 */
JNIEXPORT void JNICALL
Java_sun_awt_windows_WTextAreaPeer_replaceRange(JNIEnv *env, jobject self,
                                               jstring text,
                                               jint start, jint end)
{
    TRY;

    jobject selfGlobalRef = env->NewGlobalRef(self);
    jstring textGlobalRef = (jstring)env->NewGlobalRef(text);

    ReplaceTextStruct *rts = new ReplaceTextStruct;
    rts->textComponent = selfGlobalRef;
    rts->text = textGlobalRef;
    rts->start = start;
    rts->end = end;

    AwtToolkit::GetInstance().SyncCall(AwtTextArea::_ReplaceText, rts);
    // global refs and rts are deleted in _ReplaceText()

    CATCH_BAD_ALLOC;
}
} /* extern "C" */
