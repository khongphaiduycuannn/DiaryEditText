package com.demo.diarytextitem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demo.diarytextitem.custom.view.Bullet
import com.demo.diarytextitem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var encode: String =
        "\udc06\udc00\udc02\udc07\u0044\udc06\udc00\udc02\udc07\u0061\udc06\udc00\udc02\udc07\u006e\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u0073\udc06\udc00\udc02\udc07\u00e1\udc06\udc00\udc02\udc07\u0063\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u0074\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u00e0\udc06\udc00\udc02\udc07\u006e\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u0076\udc06\udc00\udc02\udc07\u0069\udc06\udc00\udc02\udc07\u00ea\udc06\udc00\udc02\udc07\u006e\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u006c\udc06\udc00\udc02\udc07\u1edb\udc06\udc00\udc02\udc07\u0070\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc03\u002d\u0036\u0035\u0035\u0033\u0036\udc04\u0031\u002e\u0033\u0032\u0032\u0035\udc07\u0031\udc06\udc00\udc02\udc03\u002d\u0036\u0035\u0035\u0033\u0036\udc04\u0031\u002e\u0033\u0032\u0032\u0035\udc07\u0032\udc06\udc00\udc02\udc03\u002d\u0036\u0035\u0035\u0033\u0036\udc04\u0031\u002e\u0033\u0032\u0032\u0035\udc07\u0061\udc06\udc00\udc02\udc03\u002d\u0036\u0035\u0035\u0033\u0036\udc04\u0031\u002e\u0033\u0032\u0032\u0035\udc07\u0033\udc06\udc00\udc02\udc07\u003a\udc06\udc07\u000a\udc06\udc05\u0031\udc07\u0020\udc06\udc00\udc01\udc04\u0030\u002e\u0037\u0035\u0036\u0031\u0034\u0033\u0037\u0035\udc07\u004c\udc06\udc00\udc01\udc04\u0030\u002e\u0037\u0035\u0036\u0031\u0034\u0033\u0037\u0035\udc07\u0054\udc06\udc00\udc01\udc04\u0030\u002e\u0037\u0035\u0036\u0031\u0034\u0033\u0037\u0035\udc07\u003a\udc06\udc07\u0020\udc06\udc07\u004e\udc06\udc07\u0067\udc06\udc07\u0075\udc06\udc07\u0079\udc06\udc07\u1ec5\udc06\udc07\u006e\udc06\udc07\u0020\udc06\udc07\u0044\udc06\udc07\u0075\udc06\udc07\u0079\udc06\udc07\u0020\udc06\udc07\u004d\udc06\udc07\u0069\udc06\udc07\u006e\udc06\udc07\u0068\udc06\udc07\u0020\udc06\udc07\u0051\udc06\udc07\u0075\udc06\udc07\u00e2\udc06\udc07\u006e\udc06\udc07\u000a\udc06\udc05\u0031\udc07\u0020\udc06\udc07\u0110\udc06\udc07\u1ed7\udc06\udc07\u0020\udc06\udc07\u0044\udc06\udc07\u0061\udc06\udc07\u006e\udc06\udc07\u0068\udc06\udc07\u0020\udc06\udc07\u004e\udc06\udc07\u0067\udc06\udc07\u0068\udc06\udc07\u0129\udc06\udc07\u0061\udc06\udc07\u000a\udc06\udc07\u000a\udc06\udc00\udc02\udc07\u0044\udc06\udc00\udc02\udc07\u0061\udc06\udc00\udc02\udc07\u006e\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u0073\udc06\udc00\udc02\udc07\u00e1\udc06\udc00\udc02\udc07\u0063\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u0074\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u00e0\udc06\udc00\udc02\udc07\u006e\udc06\udc00\udc02\udc07\u0068\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u0076\udc06\udc00\udc02\udc07\u0069\udc06\udc00\udc02\udc07\u00ea\udc06\udc00\udc02\udc07\u006e\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc07\u006c\udc06\udc00\udc02\udc07\u1edb\udc06\udc00\udc02\udc07\u0070\udc06\udc00\udc02\udc07\u0020\udc06\udc00\udc02\udc03\u002d\u0031\u0036\u0037\u0037\u0036\u0039\u0036\u0031\udc04\u0031\u002e\u0031\u0035\udc07\u0031\udc06\udc00\udc02\udc03\u002d\u0031\u0036\u0037\u0037\u0036\u0039\u0036\u0031\udc04\u0031\u002e\u0031\u0035\udc07\u0032\udc06\udc00\udc02\udc03\u002d\u0031\u0036\u0037\u0037\u0036\u0039\u0036\u0031\udc04\u0031\u002e\u0031\u0035\udc07\u0061\udc06\udc00\udc02\udc03\u002d\u0031\u0036\u0037\u0037\u0036\u0039\u0036\u0031\udc04\u0031\u002e\u0031\u0035\udc07\u0034\udc06\udc00\udc02\udc07\u003a\udc06\udc07\u000a\udc06\udc05\u0032\udc07\u0020\udc06\udc00\udc01\udc04\u0030\u002e\u0038\u0036\u0039\u0035\u0036\u0035\u0032\u0035\udc07\u004c\udc06\udc00\udc01\udc04\u0030\u002e\u0038\u0036\u0039\u0035\u0036\u0035\u0032\u0035\udc07\u0054\udc06\udc07\u003a\udc06\udc07\u0020\udc06\udc07\u004e\udc06\udc07\u0067\udc06\udc07\u0075\udc06\udc07\u0079\udc06\udc07\u1ec5\udc06\udc07\u006e\udc06\udc07\u0020\udc06\udc07\u0042\udc06\udc07\u00e1\udc06\udc07\u0020\udc06\udc07\u0041\udc06\udc07\u006e\udc06\udc07\u0068\udc06\udc07\u0020\udc06\udc07\u0110\udc06\udc07\u1ee9\udc06\udc07\u0063\udc06\udc07\u000a\udc06\udc05\u0032\udc07\u0020\udc06\udc07\u0110\udc06\udc07\u1ed7\udc06\udc07\u0020\udc06\udc07\u0054\udc06\udc07\u0068\udc06\udc07\u1ecb\udc06\udc07\u0020\udc06\udc07\u004e\udc06\udc07\u0068\udc06\udc07\u01b0\udc06\udc07\u0020\udc06\udc07\u0042\udc06\udc07\u00ec\udc06\udc07\u006e\udc06\udc07\u0068"

    private val listBullet = mutableListOf<Bullet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initData()
        initView()
        handleEvent()
    }

    private fun initData() {
        listBullet.add(Bullet(this, 1, R.drawable.ic_bullet_1))
        listBullet.add(Bullet(this, 2, R.drawable.ic_bullet_2))
    }

    private fun initView() {
        binding.toolBar2.llToolbar2.visibility = View.GONE

        // edtEncode và edtDecode phải có cùng listBullet
        binding.edtEncode.loadListBullet(listBullet.toList())
        binding.edtDecode.loadListBullet(listBullet.toList())

        binding.edtEncode.decode(encode)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun handleEvent() {
        val toolbar1 = binding.toolBar1
        val toolbar2 = binding.toolBar2

        binding.edtEncode.onSelectCharacter = {
            toolbar1.llToolbar1.visibility = View.GONE
            toolbar2.llToolbar2.visibility = View.VISIBLE
        }

        binding.edtEncode.onNonSelectCharacter = {
            toolbar1.llToolbar1.visibility = View.VISIBLE
            toolbar2.llToolbar2.visibility = View.GONE
        }

        with(toolbar1) {
            btnEncode.setOnClickListener {
                encode = binding.edtEncode.encode()

                var res = ""
                encode.map { "\\u${it.code.toHexString().substring(4, 8)}" }
                    .forEach { res += it }
                Log.d("Encode", res)
            }

            btnDecode.setOnClickListener {
                binding.edtDecode.decode(encode)
            }

            btnBulletStyle1.setOnClickListener {
                binding.edtEncode.setBullet(listBullet[0])
            }

            btnBulletStyle2.setOnClickListener {
                binding.edtEncode.setBullet(listBullet[1])
            }
        }

        with(toolbar2) {
            btnBold.setOnClickListener {
                binding.edtEncode.apply { setBold(selectionStart, selectionEnd) }
            }

            btnItalic.setOnClickListener {
                binding.edtEncode.apply { setItalic(selectionStart, selectionEnd) }
            }

            btnUnderline.setOnClickListener {
                binding.edtEncode.apply { setUnderLine(selectionStart, selectionEnd) }
            }

            btnUpTextSize.setOnClickListener {
                binding.edtEncode.apply { upTextSize(selectionStart, selectionEnd) }
            }

            btnDownTextSize.setOnClickListener {
                binding.edtEncode.apply { downTextSize(selectionStart, selectionEnd) }
            }

            btnColorRed.setOnClickListener {
                binding.edtEncode.apply { setTextColor(selectionStart, selectionEnd, Color.RED) }
            }

            btnColorBlue.setOnClickListener {
                binding.edtEncode.apply { setTextColor(selectionStart, selectionEnd, Color.BLUE) }
            }
        }
    }
}