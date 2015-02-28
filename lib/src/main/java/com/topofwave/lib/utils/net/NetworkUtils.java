package com.topofwave.lib.utils.net;

import com.topofwave.lib.utils.debug.DebugLog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @describe 网络通用工具类.
 * @author adison
 * @date: 2014-10-23 上午10:48:19 <br/>
 */
public class NetworkUtils {

    private NetworkUtils() {
    }

    /**
     * 获取ConnectivityManager
     */
    public static ConnectivityManager getConnManager(Context context) {
        return (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    // 在WLAN设置界面
    // 1，显示连接已保存，但标题栏没有，即没有实质连接上，输出为：not connect， available

    // 2，显示连接已保存，标题栏也有已连接上的图标， 输出为：connect， available

    // 3，选择不保存后 输出为：not connect， available

    // 4，选择连接，在正在获取IP地址时 输出为：not connect， not available

    // 5，连接上后 输出为：connect， available

    /**
     * 判断网络连接是否有效（此时可传输数据）。
     * @param context
     * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
     */
    public static boolean isConnected(Context context) {
        try {
            NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
            return net != null && net.isConnected();
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 判断有无网络正在连接中（查找网络、校验、获取IP等）。
     * @param context
     * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
     */
    public static boolean isConnectedOrConnecting(Context context) {
        NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
        if(nets != null) {
            for(NetworkInfo net: nets) {
                if(net.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接类型
     * @param context
     * @return
     */
    public static NetType getConnectedType(Context context) {
        try {
            NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
            if(net != null) {
                switch(net.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        return NetType.NET_WIFI;
                    case ConnectivityManager.TYPE_MOBILE:
                        return NetType.NET_MOBILE;
                    default:
                        return NetType.NET_OTHER;
                }
            }
            return NetType.NET_NONE;
        } catch(Exception e) {
            return NetType.NET_OTHER;
        }
    }

    /**
     * 是否存在有效的WIFI连接
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == ConnectivityManager.TYPE_WIFI && net.isConnected();
    }

    /**
     * 是否存在有效的移动连接
     * @param context
     * @return boolean
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == ConnectivityManager.TYPE_MOBILE && net.isConnected();
    }

    /**
     * 检测网络是否为可用状态
     */
    public static boolean isAvailable(Context context) {
        return isWifiAvailable(context) || (isMobileAvailable(context) && isMobileEnabled(context));
    }

    /**
     * 判断是否有可用状态的Wifi，以下情况返回false： 1. 设备wifi开关关掉; 2. 已经打开飞行模式； 3. 设备所在区域没有信号覆盖； 4. 设备在漫游区域，且关闭了网络漫游。
     * @param context
     * @return boolean wifi为可用状态（不一定成功连接，即Connected）即返回ture
     */
    public static boolean isWifiAvailable(Context context) {
        NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
        if(nets != null) {
            for(NetworkInfo net: nets) {
                if(net.getType() == ConnectivityManager.TYPE_WIFI) {
                    return net.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断有无可用状态的移动网络，注意关掉设备移动网络直接不影响此函数。 也就是即使关掉移动网络，那么移动网络也可能是可用的(彩信等服务)，即返回true。 以下情况它是不可用的，将返回false： 1. 设备打开飞行模式； 2.
     * 设备所在区域没有信号覆盖； 3. 设备在漫游区域，且关闭了网络漫游。
     * @param context
     * @return boolean
     */
    public static boolean isMobileAvailable(Context context) {
        NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
        if(nets != null) {
            for(NetworkInfo net: nets) {
                if(net.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return net.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 设备是否打开移动网络开关
     * @param context
     * @return boolean 打开移动网络返回true，反之false
     */
    public static boolean isMobileEnabled(Context context) {
        try {
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean)getMobileDataEnabledMethod.invoke(getConnManager(context));
        } catch(Exception e) {
            DebugLog.i(e.getMessage());
        }
        // 反射失败，默认开启
        return true;
    }

    /**
     * 返回Wifi是否启用
     * @param context 上下文
     * @return Wifi网络可用则返回true，否则返回false
     */
    public static boolean isWIFIActivate(Context context) {
        return ((WifiManager)context.getSystemService(Context.WIFI_SERVICE)).isWifiEnabled();
    }

    /**
     * 修改Wifi状态
     * @param context 上下文
     * @param status true为开启Wifi，false为关闭Wifi
     */
    public static void changeWIFIStatus(Context context, boolean status) {
        ((WifiManager)context.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(status);
    }

    /**
     * 打印当前各种网络状态
     * @param context
     * @return boolean
     */
    public static boolean printNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null) {
            NetworkInfo in = connectivity.getActiveNetworkInfo();
            DebugLog.i("-------------$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-------------");
            DebugLog.i("getActiveNetworkInfo: " + in);
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null) {
                for(int i = 0; i < info.length; i ++ ) {
                    // if (info[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    DebugLog.i("NetworkInfo[" + i + "]isAvailable : " + info[i].isAvailable());
                    DebugLog.i("NetworkInfo[" + i + "]isConnected : " + info[i].isConnected());
                    DebugLog.i("NetworkInfo[" + i + "]isConnectedOrConnecting : " + info[i].isConnectedOrConnecting());
                    DebugLog.i("NetworkInfo[" + i + "]: " + info[i]);
                    // }
                }
                DebugLog.i("\n");
            } else {
                DebugLog.i("getAllNetworkInfo is null");
            }
        }
        return false;
    }

    /**
     * getIpAddress:获取本机网络IP. <br/>
     * @author adison
     * @param context
     * @return
     */
    public static String getIpAddress(Context context) {
        try {
            NetType type = NetworkUtils.getConnectedType(context);
            if(type != null) {
                if(NetType.NET_MOBILE == type) {
                    return getMobileIPAddress();
                } else if(NetType.NET_WIFI == type) {
                    return getWifiIPAddress(context);
                }
            }
        } catch(Exception e) {
            DebugLog.e("getIp-->Error::", e);
        }
        return "127.0.0.1";
    }

    /**
     * getMobileIPAddress:获取手机网络ip地址. <br/>
     * @author adison
     * @return
     */
    public static String getMobileIPAddress() {
        try {
            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if( ! inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch(Exception e) {
            DebugLog.e("getIp-->Error::", e);

        }
        return "127.0.0.1";
    }

    /*
     * 获取wifi IP地址
     */
    public static String getWifiIPAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            // 获取32位整型IP地址
            int ipAddress = wifiInfo.getIpAddress();
            // 返回整型地址转换成“*.*.*.*”地址
            return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        } catch(Exception e) {
            
            DebugLog.e("getIp-->Error::", e);
            
        }
        return "127.0.0.1";
    }

}
