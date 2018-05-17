package com.me.harris.tipdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = "ThirdActivity";
    public static final String content = "原标题：天津发布人才引进新政：本科毕业不超40岁直接落户\n" +
            "\n" +
            "5月16日，第二届世界智能大会开幕式上，天津发布了新的人才引进政策——“海河英才”行动计划。新政策大幅放宽人才落户条件、自主选择落户地点、简化落户办理程序。\n" +
            "新政策门槛有多低？不用缴社保！不用居住证！\n" +
            "\n" +
            "学历型人才：全日制高校本科毕业生不超过40周岁、硕士研究生不超过45周岁、博士研究生不受年龄限制，就可以直接在天津落户，不需要其他任何条件。\n" +
            "\n" +
            "资格型人才：获得副高级及以上职称，以及拥有国内外精算师、注册会计师、注册税务师、注册建筑师、律师等执业资格的，可直接落户天津。\n" +
            "\n" +
            "技能型人才：高等职业院校毕业并在天津工作满1年或中等职业院校毕业并在天津工作满3年，具有高等职业资格、不超过35周岁，具有技师职业资格、不超过40岁，具有高等技师职业资格、不超过50周岁的，可直接落户天津。\n" +
            "\n" +
            "创业型人才：创办符合天津产业政策且企业稳定运行超过1年，个人累计缴纳所得税10万万以上的，可直接落户天津。\n" +
            "\n" +
            "新突破：急需型人才企业做主！\n" +
            "\n" +
            "对人工智能、生物医药、新能源新材料等战略性新兴产业领域重点企业的急需型人才，由企业家自主确定落户条件。\n" +
            "\n" +
            "\n" +
            "天津市人力资源和社会保障局局长杨光：领军型企业，由企业家自己说了算，企业家说需要什么样的急需人才，由他们自己定标准，政府是照单办理，照单全收，对人才的认定、引进、评价，突出向市场放权。\n" +
            "\n" +
            "无房没单位怎么办？人才市场可落户！\n" +
            "\n" +
            "来津人才可以自主选择在本人或配偶名下的产权房，或所在单位集体户落户。\n" +
            "\n" +
            "无产权房、无就业单位或所在单位无集体户的人才，可选择在指定的公共服务机构的人才集体户落户并存档。\n" +
            "\n" +
            "几天能办完？3天反馈审核结果！\n" +
            "\n" +
            "下载“天津公安”手机APP或登录“天津公安”民生服务平台可在线提出落户申请，公安部门3个工作日内反馈审核结果。审核通过后30日内，携带材料、最多跑一次，在行政许可服务中心办理落户手续。";

    MaxLineTextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        textView = findViewById(R.id.tv_content);
        textView.setText(content);


    }
}
