### RestClient 集成一套网络请求框架  使用 okhttp + retrofit 等网络请求框架进行二次封装，使用方便

### 如何使用？

#### 步骤一：在项目build.gradle加上
```
repositories {
    maven { url 'https://jitpack.io' }
}
```
#### 步骤二：在moduble的build.gradle加上

```
dependencies {
    implementation 'com.github.wxianing:RestClient:v1.0.0'
}
```

### 代码具体调用说明

#### 一、初始化配置， 要使用该框架，则必须先在Application中初始化配置，否则使用到配置APP会闪退，配置代码如下： 

``` 
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化常用配置
         Config.init(this)
                .withLoaderDelayed(500)
                .withApiHost("http://xxx.com/")//你的服务域名或者IP
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .configure();
    }
}

``` 

#### 二、网络请求使用

##### 1.post请求方式使用

``` 
RestClient.builder()
        .url("")//请求地址url，不包含域名端口
        .params("", "")//参数，可添加多个
        .loader(this)//loading加载动画
        .success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                //成功回调
                LogUtil.i(response);
            }
        })
        .error(new IError() {
            @Override
            public void onError(int code, String msg) {
                 //错误回调
            }
        })
        .build()
        .post();
                
``` 

##### 2.get请求方式使用

``` 
RestClient.builder()
        .url("")//请求地址url，不包含域名端口
        .params("", "")//参数，可添加多个
        .loader(this)//loading加载动画
        .success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                 //成功回调
                 LogUtil.i(response);
            }
        })
        .error(new IError() {
            @Override
            public void onError(int code, String msg) {
                 //错误回调
            }
        })
        .build()
        .get();
                
``` 

##### 3.文件上传

``` 
RestClient.builder()
        .url("")//请求地址url，不包含域名端口
        .params("", "")//参数，可添加多个
        .file(file)//要上传的文件
        .loader(this)//loading加载动画
        .success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                //成功回调
                LogUtil.i(response);
            }
        })
        .error(new IError() {
            @Override
            public void onError(int code, String msg) {
                //错误回调
            }
        })
        .build()
        .upload();

``` 

##### 4.下载文件

``` 
RestClient.builder()
        .url("")//请求地址url，不包含域名端口
        .params("", "")//参数，可添加多个
        .extension("apk")//文件后缀名
        .dir("/")//保存到文件夹
        .loader(this)//loading加载动画
        .success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                //成功回调
                LogUtil.i(response);
            }
        })
        .error(new IError() {
            @Override
            public void onError(int code, String msg) {
                //错误回调
            }
        })
        .build()
        .download();
                
``` 
