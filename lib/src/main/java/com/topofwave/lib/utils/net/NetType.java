package com.topofwave.lib.utils.net;

/**
 * @describe 网络类型.
 * @author adison
 * @date: 2014-10-23 上午10:48:00 <br/>
 */
public enum NetType {
    /**
     * 无网络
     */
    NET_NONE(1),
    /**
     * 手机
     */
    NET_MOBILE(2),
    /**
     * wifi
     */
    NET_WIFI(4),
    /**
     * 其他
     */
    NET_OTHER(8);

    NetType(int value) {
        this.value = value;
    }

    public int value;
}
