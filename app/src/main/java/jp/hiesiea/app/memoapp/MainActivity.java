package jp.hiesiea.app.memoapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * メインアクティビティ
 * メモ帳アプリのメインプログラム
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 入力されたタイトルに対応するキー名(定数)
    private static final String KEY_TITLE = "title";

    // 入力されたコメントに対応するキー名(定数)
    private static final String KEY_COMMENT = "comment";

    // タイトル入力用エディットテキスト
    private EditText mEditTextTitle;

    // コメント入力用エディットテキスト
    private EditText mEditTextComment;

    // プリファレンス
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // レイアウトよりEditTextを取得
        mEditTextTitle = (EditText) findViewById(R.id.main_edit_text_title);
        mEditTextComment = (EditText) findViewById(R.id.main_edit_text_comment);

        // プリファレンスをデフォルト名で作成
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // プリファレンスにデータが保存されていれば、保存されているデータをセットする
        // タイトル
        String title = mPreferences.getString(KEY_TITLE, getResources().getString(R.string.main_not_found_data));
        if (!title.equals(getResources().getString(R.string.main_not_found_data))) {
            mEditTextTitle.setText(title);
        }

        // コメント
        String comment = mPreferences.getString(KEY_COMMENT, getResources().getString(R.string.main_not_found_data));
        if (!comment.equals(getResources().getString(R.string.main_not_found_data))) {
            mEditTextComment.setText(comment);
        }

        // 保存ボタンにリスナーを設定する
        findViewById(R.id.main_button_save).setOnClickListener(this);

        // 削除ボタンにリスナーを設定する
        findViewById(R.id.main_button_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // プリファレンスエディタの初期化
        SharedPreferences.Editor editor = mPreferences.edit();
        switch (view.getId()) {
            case R.id.main_button_save:

                // 入力されているデータを取得
                String title = mEditTextTitle.getText().toString();
                String comment = mEditTextComment.getText().toString();

                // プリファレンスにデータを保存
                editor.putString(KEY_TITLE, title);
                editor.putString(KEY_COMMENT, comment);
                editor.commit();

                // Toastを表示し、保存が完了した旨を通知する
                Toast.makeText(MainActivity.this, getResources().getString(R.string.main_toast_save), Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_button_delete:

                // プリファレンスからデータを削除
                editor.remove(KEY_TITLE);
                editor.remove(KEY_COMMENT);
                editor.commit();

                // 入力欄を空にする
                mEditTextTitle.setText("");
                mEditTextComment.setText("");

                // Toastを表示し、削除が完了した旨を通知する
                Toast.makeText(MainActivity.this, getResources().getString(R.string.main_toast_delete), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
