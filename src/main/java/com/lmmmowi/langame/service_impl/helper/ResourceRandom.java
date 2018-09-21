package com.lmmmowi.langame.service_impl.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class ResourceRandom {

    private static final List<String> AVATARS = Arrays.asList(
            "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png",
            "https://gw.alipayobjects.com/zos/rmsportal/cnrhVkzwxjPwAaCfPbdc.png",
            "https://gw.alipayobjects.com/zos/rmsportal/gaOngJwsRYRaVAuXXcmB.png",
            "https://gw.alipayobjects.com/zos/rmsportal/WhxKECPNujWoWEFNdnJE.png",
            "https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png",
            "https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png"
    );

    public static final String NICKNAME_PART_A = "豁达,潇洒,雍容,轩昂,爽朗,悠然,从容,坦荡,大方,宽容,厚道,风度,高雅,情调,淡泊,迷人,安然,宁静,随和,随性,傲骨,大气,柔韧,洋气,海涵,儒雅,淡定,漂亮，可爱，美丽,黑暗,强壮,丑陋,小巧,精致";
    public static final String NICKNAME_PART_B = "狮子,老虎,天鹅,鸭子,乌龟,海豚,猴子,孔雀,鲤鱼,鲨鱼,青蛙,蟾蜍,企鹅,海豹,狐狸,乌鸦,灰鹤,白鹭,云雀,刺猬,螃蟹,鼹鼠,蝙蝠,熊猫,海狮,海象,海牛,河马,犀牛,斑马,骆驼,猩猩,蜗牛,鳄鱼,蜘蛛,蝎子,蜈蚣,蝴蝶,蛤蟆,蜥蜴,雪貂,蜂鸟,鲶鱼,松鼠,袋鼠,螳螂,蚂蚱,大雁,蚂蚁,恐龙,鳗鱼,河豚,海马,鳕鱼,鲫鱼,鲟鱼";


    public static String getRandomAvatar() {
        Random random = new Random(System.currentTimeMillis());
        return AVATARS.get(random.nextInt(AVATARS.size()));
    }

    public static String getRandomNickname(){
        Random random = new Random(System.currentTimeMillis());
        String[] a = NICKNAME_PART_A.split(",");
        String[] b = NICKNAME_PART_B.split(",");
        String sa = a[random.nextInt(a.length)];
        String sb = b[random.nextInt(b.length)];
        return sa + "的" + sb;
    }

}
