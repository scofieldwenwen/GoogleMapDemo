#Google地图开发相关功能文档说明
##开发准备工作
* 开发工具: Android Studio
* 新建一个项目: 添加Google Play 服务 SDK. 在gradle中加上 `compile 'com.google.android.gms:play-services:8.4.0'`
* 获取 Google Maps API 密钥: [创建一个应用](https://console.developers.google.com/project),启用Google Maps Android API,需要提供应用包名和签名文件的SHA1(冒号也要)
* 手机: 需要有Google Mobile Service,国内手机需要翻墙.

##配置清单文件
* 
**权限添加：**   
`<uses-permission android:name="android.permission.INTERNET" />`  //由 API 用于从 Google 地图服务器下载地图图块 。 <br>
`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/> ` //允许 API 检查连接状态，以确定是否可以下载数据 。<br>
`<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />`  //粗略位置权限 : 允许 API 利用 WiFi 或移动蜂窝数据（或同时利用两者）来确定设备位置。<br>
`<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />`// 精确位置权限 : 允许 API 利用包括全球定位系统 (GPS) 在内的可用位置提供商以及 WiFi 和移动蜂窝数据尽可能精确地确定位置。<br>

*  **添加 API 密钥:**<br>
在 AndroidManifest.xml 中，通过在 </application> 结束标记前插入以下元素:
`<meta-data
android:name="com.google.android.geo.API_KEY"
android:value="YOUR_API_KEY" />` //
用您的 API 密钥替代 value 属性中的 YOUR_API_KEY。该元素会将密钥 com.google.android.geo.API_KEY 设置为您的 API 密钥的值。


##创建地图(MapFragment 对象或 MapView)
###创建地图说明
如需在创建地图时应用这些选项，请执行下列操作之一：

* 如果您使用的是 `MapFragment`，请使用` MapFragment.newInstance`(GoogleMapOptions options) 静态出厂方法构建 Fragment 并传入您的自定义配置选项
* 如果您使用的是 `MapView`，请使用` MapView(Context, GoogleMapOptions)` 构造函数并传入您的自定义配置选项

###添加地图方法

1.添加 `Fragment`:<br>
方法一:<br>
向 `Activity` 的布局文件添加 <fragment> 元素，以定义 `Fragment` 对象。 
<p>以下布局文件包含一个 <code>&lt;fragment&gt;</code> 元素：</p>
<pre class="prettyprint notranslate" translate="no"><code>&lt;?xml version="1.0" encoding="utf-8"?&gt;
&lt;fragment xmlns:android="http://schemas.android.com/apk/res/android"
    android:name="com.google.android.gms.maps.MapFragment"
    android:id="@+id/map"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/&gt;
</code></pre>
方法二:<br>
向代码中的 `Activity` 添加 `MapFragment`。 如需实现此目的，请创建一个新的 `MapFragment` 实例，然后调用` FragmentTransaction.add()`，将 `Fragment` 添加到当前 `Activity`
<pre><code> mMapFragment = MapFragment.newInstance();
 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
 fragmentTransaction.add(R.id.my_container, mMapFragment);
 fragmentTransaction.commit();
</code></pre>

<p>**注意:如果在Fragment中嵌套使用MapFragment,进行如下操作:**
<p>父`Fragment`的xml代码

    <include layout="@layout/layout_title_bar" />
    <FrameLayout
        android:id="@+id/map"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </FrameLayout>

<p>父`Fragment`代码中
<pre><code>FragmentManager fm = getChildFragmentManager();
        fragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.map, fragment).commit();
</code></pre>

###添加地图代码
<p>在应用内使用地图，需要实现
<code>OnMapReadyCallback</code> 接口，并在
<code>MapFragment</code>对象或 <code>MapView</code> 对象上设置回调实例。
 本教程使用的是 <code>MapFragment</code>，因为这是向应用添加地图的最常用方法。
 第一步是实现回调接口：</p>
<pre class="prettyprint notranslate" translate="no"><code>public class MainActivity extends FragmentActivity
    implements OnMapReadyCallback {
...
}
</code></pre>

<p>然后使用 <code>getMapAsync()</code> 设置 Fragment 上的回调。</p>
<pre class="prettyprint notranslate" translate="no"><code>MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
mapFragment.getMapAsync(this);
</code></pre>

<p>注：Google Maps Android API 需要 API 级别 12 或更高级别，才能支持 <code>MapFragment</code> 对象。如果您的目标是低于 API级别 12 的应用，可通过<code>SupportMapFragment</code> 类访问同一功能。 您还必须提供 Android支持库。</p>

<p>注：必须从主线程调用 <code>getMapAsync()</code>，回调将在主线程中执行。
 如果用户设备上未安装 Google Play
服务，则用户安装 Play 服务后才会触发回调。</p>
 回调将在地图做好使用准备时触发:
<pre class="prettyprint notranslate" translate="no"><code>@Override
public void onMapReady(GoogleMap map) {
    map.addMarker(new MarkerOptions()
   .position(new LatLng(0, 0))
        .title("Marker"));
}
</code></pre>

###配置初始状态
<p>据您的应用的需求配置地图的初始状态</p>
* 地图显示位置，包括：位置、缩放比例、方位和倾斜角度。
* 地图类型
* 缩放按钮和/或指南针是否出现在屏幕上
* 用户在地图时可使用的手势
<code><pre>
 public void onMapReady(GoogleMap googleMap) {
	        mMap = googleMap;
    	//Latlng为坐标
    	private LatLng beijing = new LatLng(39.915378, 116.393058);//北京
    	mMap.moveCamera(CameraUpdateFactory.newLatLng(beijing));//地图的焦点
 		UiSettings uiSettings = mMap.getUiSettings();
    	uiSettings.setZoomControlsEnabled(true)//缩放比例控件显示
    	uiSettings.setCompassEnabled(true)//指南针显示
    	uiSettings.setMyLocationButtonEnabled(true)//定位自己位置显示
		uiSettings.setMapToolbarEnabled(false)//禁用该工具栏(跳转到谷歌地图app)
    }
</code></pre>

###使用 XML 属性
需要添加命名空间:
<code>xmlns:map="http://schemas.android.com/apk/res-auto"<br>
下面这段 XML 代码展示了如何配置带有一些自定义选项的 MapFragment。 这些属性同样可以应用于 MapView。
<code><pre>
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:map="http://schemas.android.com/apk/res-auto"
  android:name="com.google.android.gms.maps.SupportMapFragment"
  android:id="@+id/map"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  map:cameraBearing="112.5"
  map:cameraTargetLat="-33.796923"
  map:cameraTargetLng="150.922433"
  map:cameraTilt="30"
  map:cameraZoom="13"
  map:mapType="normal"
  map:uiCompass="false"
  map:uiRotateGestures="true"
  map:uiScrollGestures="false"
  map:uiTiltGestures="true"
  map:uiZoomControls="false"
  map:uiZoomGestures="true"/>
</code></pre>

##地图交互
###控件
* 缩放比例空间 `UiSettings.setZoomControlsEnabled(boolean)`
* 指南针 `UiSettings.setCompassEnabled(boolean)`
* 自己定位 `UiSettings.setMyLocationButtonEnabled(boolean)`
* 地图工具栏  `UiSettings.setMapToolbarEnabled(boolean)`

###手势
* 缩放手势 `UiSettings.setZoomGesturesEnabled(boolean)`
* 滚动手势 `UiSettings.setScrollGesturesEnabled(boolean)`
* 倾斜手势 `UiSettings.setTiltGesturesEnabled(boolean)`
* 旋转手势 `UiSettings.setRotateGesturesEnabled(boolean)`

###事件
* 地图点击事件 实现OnMapClickListener接口,回调 onMapClick(LatLng) 事件
* 地图长点击事件 实现OnMapLongClickListener接口,回调 onMapLongClick(LatLng) 事件
* 摄像头变化事件 实现OnCameraChangeListener接口,调用GoogleMap.setOnCameraChangeListener(OnCameraChangeListener) 在地图上进行设置。<br>当摄像头发生变化时，侦听器通过 onCameraChange(CameraPosition) 回调接收通知。
* marker点击事件 实现OnMarkerDragListener接口,回调onMarkerClick(Marker marker)事件,return false显示infowindow,return ture隐藏infowindow
* marker拖动事件 实现OnMarkerDragListener接口,设置标记为可拖动marker.setDraggable(true),回调三个方法DragStart,Drag,DragEnd.

###视图与缩放
* 更改缩放比例
<p><code>CameraUpdateFactory.zoomIn()</code></a> 和
<code>CameraUpdateFactory.zoomOut()</code></a>
为您提供的 <code>CameraUpdate</code> 可将缩放比例更改 1.0，所有其他属性保持不变。</p>
<p><code>CameraUpdateFactory.zoomTo(float)</code></a>
为您提供的 <code>CameraUpdate</code> 可将缩放比例更改为给定值，所有其他属性保持不变。</p>
<p><<code>CameraUpdateFactory.zoomBy(float)</code></a>
和<code>CameraUpdateFactory.zoomBy(float, Point)</code></a>
为您提供的 <code>CameraUpdate</code> 可使缩放比例增加（如果是负值，则减少）给定值。<br>后者会将给定点固定在屏幕上以使其保持在同一位置（经度/纬度），因此它可能会更改摄像头的位置以实现此目的。</p>
* 更改摄像头位置
<p>可利用两种便捷方法完成常见的位置更改。
<code>CameraUpdateFactory.newLatLng(LatLng)</code></a>
为您提供的 <code>CameraUpdate</code> 可更改摄像头的经度和纬度，所有其他属性保持不变<br><code>CameraUpdateFactory.newLatLngZoom(LatLng, float)</code></a>
为您提供的 <code>CameraUpdate</code></a> 可更改摄像头的经度、纬度和缩放比例，所有其他属性保持不变。</p>
<p>如需在更改摄像头位置上获得最大灵活性，请使用
<code>CameraUpdateFactory.newCameraPosition(CameraPosition)</code></a>，它为您提供的 <code>CameraUpdate</code></a> 可将摄像头移至给定位置。<br>
 <code>CameraPosition</code> 可直接获得，利用
<code>new CameraPosition()</code> 获得，或者通过 <code>CameraPosition.Builder</code> 利用
<code>new CameraPosition.Builder()</code> 获得。</p>
* 设置边界
<p>有时，通过移动摄像头来以尽可能最高的缩放级别显示整个受关注区域很有用处。
 例如，
如果您要显示用户当前位置方圆五英里内的所有加油站，可能就需要通过移动摄像头让它们<br>
全都显示在屏幕上。如需实现此目的，请先计算您想在屏幕上显示的 <code>LatLngBounds</code></a>。然后使用
<code>CameraUpdateFactory.newLatLngBounds(LatLngBounds bounds, int padding)</code> <br>获取
<code>CameraUpdate</code></a>，后者会相应更改摄像头位置，使得给定
<code>LatLngBounds</code> 在计入所指定内边距（单位：像素）后能够完全容纳在地图内。
 返回的 <code>CameraUpdate</code>
可确保给<br>定边界与地图边缘之间的间隙（单位：像素）至少与指定的内边距一样大。
 请注意，地图的倾斜角度和方位均为 0。</p>
<pre class="prettyprint notranslate" translate="no"><code>private GoogleMap mMap;
// Create a LatLngBounds that includes Australia.
private LatLngBounds AUSTRALIA = new LatLngBounds(
  new LatLng(-44, 113), new LatLng(-10, 154));

// Set the camera to the greatest possible zoom level that includes the
// bounds
mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));
</code></pre>
<p>在某些情况下，您可能想让摄像头位于边界内的中心位置，而不将极端边界包括在内。
 例如，让摄像头位于某一国家的中心位置，并保持缩放比例不变。
 在此情况下，您可以采用<br>类似的方法：
创建一个<code>LatLngBounds</code></a>，并将
<code>CameraUpdateFactory.newLatLngZoom(LatLng latLng, float zoom)</code> 与
<code>LatLngBounds</code>.<code>getCenter()</code> 方法结合使用。<br> getCenter() 方法将返回
<code>LatLngBounds</code> 的地理中心。</p>
<pre class="prettyprint notranslate" translate="no"><code>private GoogleMap mMap;
private LatLngBounds AUSTRALIA = new LatLngBounds(
  new LatLng(-44, 113), new LatLng(-10, 154));

mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 10));
</code></pre>
<p>该方法的重载<code>newLatLngBounds(boundary, width, height,
padding)</code></a>
允许您以像素为单位指定矩形的宽度和高度，以便对应地图的尺寸。
 矩形将进行相应定位，
使其<br>与地图视图具有相同的中心（这样一来，如果指定的尺寸与地图视图的尺寸相同，矩形便会与地图视图重合）。
 返回的
<code>CameraUpdate</code> 将对摄像头进行相应移动，使得指定的<br>
<code>LatLngBounds</code> 在计入所需内边距后能够在可能的最高缩放比例下在屏幕上的给定矩形内居中显示。</p>
<p class="note"><strong>注：</strong>使用更简单方法
  <code>newLatLngBounds(boundary, padding)</code>
  生成 <code>CameraUpdate</code> 的唯一前提是，后者将用于在地图经历布局调整后移动摄像头。
 在布局期间，
API 会<br>计算正确投影边界框所需的地图显示边界。
 相比之下，您可以随时使用更复杂的方法
 <code>newLatLngBounds(boundary, width, height, padding)</code> 返回的
 <code>CameraUpdate</code>，<br>
  甚至可以在地图经历布局调整之前使用，因为 API 是根据您传递的参数来计算显示边界。
</p>
<p>**注意:**显示地图上所有点的最好使用实现<code>GoogleMap.OnMapLoadedCallback</code>接口,重写<code>onMapLoaded()</code>方法
<code><pre>
map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() { 
  @Override 
  public void onMapLoaded() { 
	private LatLng shenzheng = new LatLng(22.547621, 113.946277);//深圳
    	private LatLng xiamen = new LatLng(24.478869, 118.085516);//厦门
      	LatLngBounds bounds = new LatLngBounds(shenzheng, xiamen);
    	map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));//30为距离边界的长度
  } 
}); 
</code></pre>

##在地图上绘制
###标记
* 标记是 Marker 类型的对象，通过 GoogleMap.addMarker(markerOptions) 方法向地图添加
<pre><code> map.addMarker(new MarkerOptions()
        .position(new LatLng(10, 10))
        .title("Hello world"));</pre></code>

* 使标记可拖动`Marker.setDraggable(boolean)`
* 定制标记属性有:<br>Position(必须要有),Anchor,Alpha,Title(标题),Snippet(显示在标题下方的附加文本),Draggable,Visible,Flat,Rotation 或 Billboard 朝向
* 定制标记颜色<br>
可通过向 icon() 方法传递 BitmapDescriptor 对象定制默认标记图像的颜色。 <br>
可以使用 BitmapDescriptorFactory 对象中的一组预定义颜色，或者通过 BitmapDescriptorFactory.defaultMarker(float hue) 方法设置自定义标记颜色。<br>
色调是一个介于 0 和 360 之间的值，表示色轮上的点。

###信息窗口
* 显示/隐藏信息窗口: mark调用hideInfoWindow()/showInfoWindow()
* 自定义信息窗口:<br>
创建 InfoWindowAdapter 接口的具体实现，然后通过您的实现调用 GoogleMap.setInfoWindowAdapter()<br>
实现的方法： getInfoWindow(Marker) 方法和 getInfoContents(Marker) 方法。
* 信息窗口事件: 用 OnInfoWindowClickListener 来侦听信息窗口上的点击事件

###形状
1.Polyline 是一系列相连的线段，可组成您想要的任何形状，并可用于在地图上标记路径和路线<br>
2.Polygon 是一种封闭形状，可用于在地图上标记区域<br>
3.Circle 是投影在绘制于地图上的地球表面上地理位置准确的圆圈<br>

* 多段线
<p>Polyline 类在地图上定义一组相连的线段。Polyline 对象包含一组LatLng 位置.<br>
通过调用 GoogleMap.addPolyline(PolylineOptions) 向地图添加多段线。
<p>下面这段代码说明了如何向地图添加矩形：</p>
<pre class="prettyprint notranslate" translate="no"><code>// Instantiates a new Polyline object and adds points to define a rectangle
PolylineOptions rectOptions = new PolylineOptions()
        .add(new LatLng(37.35, -122.0))
        .add(new LatLng(37.45, -122.0))  // North of the previous point, but at the same longitude
        .add(new LatLng(37.45, -122.2))  // Same latitude, and 30km to the west
        .add(new LatLng(37.35, -122.2))  // Same longitude, and 16km to the south
        .add(new LatLng(37.35, -122.0)); // Closes the polyline.

// Get back the mutable Polyline
Polyline polyline = myMap.addPolyline(rectOptions);
</code></pre>

* 多边形自动完成
<p>将任何给定路径的最后一个坐标连回第一个坐标，自动“封闭”任何多边形。
<pre><code>Polygon polygon = map.addPolygon(new PolygonOptions()
         .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5))
         .strokeColor(Color.RED)
         .fillColor(Color.BLUE));
</pre></code>

* 圆
<p>center 作为 LatLng。
<br>radius（单位：米）。
<p>以下这段代码通过构建一个 CircleOptions 对象并调用 GoogleMap.addCircle(CircleOptions)，向地图添加了一个圆：
<pre><code>// Instantiates a new CircleOptions object and defines the center and radius
CircleOptions circleOptions = new CircleOptions()
    .center(new LatLng(37.4, -122.1))
    .radius(1000)); // In meters

// Get back the mutable Circle
Circle circle = myMap.addCircle(circleOptions);
</pre></code>
<p>可以调用 Circle.setRadius() 或 Circle.setCenter() 并提供新值。

* 测地线段
<p>测地设置只适用于多段线和多边形。 它不适用于圆
<p>可通过调用 *Options.geodesic() 在形状的 option 对象上设置此属性，true 表示应将线段绘制为测地线段，false 表示应将线段绘制为直线。 如果未指定，则默认绘制非测地线段 (false)。
<p>向地图添加形状后，可通过调用 isGeodesic() 获取测地设置，并可通过调用 setGeodesic() 更改测地设置。