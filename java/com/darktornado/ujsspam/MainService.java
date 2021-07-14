package com.darktornado.ujsspam;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

public class MainService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private boolean disable = false;
    private int n = 0;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keys_layout);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        final InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        switch (primaryCode) {
            case 1:
                autoInput(ic, "엄");
                break;
            case 2:
                autoInput(ic, "준");
                break;
            case 3:
                autoInput(ic, "식");
                break;
            case 4:
                disable = true;
                Toast.makeText(this, "비활성화", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Intent intent = new Intent(MainService.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStartInput(EditorInfo editorInfo, boolean bl) {
        super.onStartInput(editorInfo, bl);
        if (!MainActivity.auto) return;
        if (disable) return;
        String[] arr = {"엄", "준", "식"};
        getCurrentInputConnection().commitText(arr[n % 3], 0);
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        n++;
    }

    private void autoInput(InputConnection ic, String txt) {
        ic.commitText(txt, 1);
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
