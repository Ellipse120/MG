var autonavi_yum = {};
autonavi_yum.hostAddr = "http://nh.emap.yum.com.cn/search/gate";// 搜索、下拉提示服务器地址;
																// IOS 系统
autonavi_yum.brands = "";// 品牌
autonavi_yum.cityName = "";// 城市名
autonavi_yum.parentAddr = "";// 父地址控件
autonavi_yum.callFun = "";// 回调函数
autonavi_yum.charencode = "UTF-8";// 字符编码;

autonavi_yum.isOnline = true; 
autonavi_yum.yumtime = 1000;
autonavi_yum.emaptime = 200;
autonavi_yum.interval = autonavi_yum.yumtime;
autonavi_yum.script = null;
var storeCode, poddingId, poddingName, dayName, nightName;
var storeCode2, poddingId2, poddingName2, dayName2, nightName2;
var currentObject,currentItem,resultAddress;

function showEMapCommon(cityName, brands, parentAddr, callback) {
	autonavi_yum.cityName = cityName;
	autonavi_yum.brands = brands;
	autonavi_yum.parentAddr = parentAddr;
	autonavi_yum.callFun = callback;
	var addressCont = $("#map_cont");
	$("#emapPopup").dialog({
		resizable : false,
		height : addressCont.height(),
		width : addressCont.width(),
		dialogClass : "emap_bg",
		position : {
			my : "center",
			at : "center",
			of : window,
			collision : "fit"
		},
		modal : true
	});

	var top = $(window).height() / 2 - 183;
	var right = $(window).width() / 2 - 370;

	$("#emapPopup").find("#addressresult").html("");
	$("#emapPopup").find("#searchaddr").val($('#' + parentAddr).val());

	var zoomInObj = $("#emapPopup").find("#zoomin");
	// zoomInObj.css('cursor','pointer');

	zoomInObj.click(function() {
		try {
			if (mapObj) {
				mapObj.zoomIn();
			}
		} catch (e) {
			alert(e.message);
		}

	});

	var zoomOutObj = $("#emapPopup").find("#zoomout");
	// zoomOutObj.css('cursor','pointer');
	zoomOutObj.click(function() {
		try {
			if (mapObj) {
				mapObj.zoomOut();
			}
		} catch (e) {
			alert(e.message);
		}

	});

	var searchButtonObj = $("#emapPopup").find("#searchbutton");
	// searchButtonObj.css('cursor','pointer');
	searchButtonObj.click(function() {
		// searchEMapAddress($("#new_city").val(),
		// $("#emapPopup").find("#searchaddr").val());
		searchEMapAddress(cityName, $("#emapPopup").find("#searchaddr").val());
	});

	// searchEMapAddress($("#new_city").val(),
	// $("#emapPopup").find("#searchaddr").val());
	searchEMapAddress(cityName, $("#emapPopup").find("#searchaddr").val());
}

function searchEMapAddress(cityName, addr) {
	searchEMapAddressCommon(cityName, addr, keywordSearch_CallBack);
}

function searchEMapAddressCommon(cityName, addr, callFun) {
	var MSearch;
	AMap.service([ "AMap.PlaceSearch" ], function() {
		MSearch = new AMap.PlaceSearch({ // 构造地点查询类
			pageSize : 30,
			pageIndex : 1,
			city : cityName, // 城市
			extensions : 'all'
		});
		// 关键字查询
		MSearch.search(addr, function(status, result) {
			if (status === 'complete' && result.info === 'OK') {
				callFun(result.poiList.pois);
			}
		});
	});
}
function keywordSearch_CallBack(data) {
	var poiArr = data;
	var resultCount = poiArr.length;
	var resultHtml = "<ul >";
	if (resultCount == 0) {
		alert('没有找到,请重新输入再查找!');
		return;
	}
	for (var i = 0; i < resultCount; i++) {
		var addr = poiArr[i].name;
		if (poiArr[i].address != "" && poiArr[i].address != poiArr[i].name) {
			addr = poiArr[i].name + "(" + poiArr[i].address + ")";
		}
		if (addr.length > 22) {
			addr = addr.substr(0, 22) + "<br>" + addr.substr(22);
		}
		if (addr.length > 11) {
			addr = addr.substr(0, 11) + "<br>" + addr.substr(11);
		}

		resultHtml = resultHtml
				+ "<li style='width:200px;line-height:30px;border-bottom:1px dotted #5d8602;word-break:break-all;word-warp:break-word;'>"
				+ (Number(i) + 1) + "、<span  class='selectedaddr' ind=" + i
				+ " style='cursor:pointer;'>" + addr;
		resultHtml = resultHtml
				+ "</span><span class='comfirmn'><br><font class='font1' style='cursor:pointer;' color='red'>确认地址</font></span>"
		resultHtml = resultHtml
				+ "<font class='font2' color='red'>*很抱歉,该地址不在外送范围内,请<br>重新填写!</font></span></li>"
	}

	if (poiArr && poiArr.length > 0) {
		showAddressInMap(poiArr[0]);
	}

	resultHtml = resultHtml
			+ "<li style='width:200px;height:100px;line-height:30px;word-break:break-all;word-warp:break-word;'></li>"
	$("#emapPopup").find("#addressresult").html(resultHtml);
	$("#emapPopup").find("li").find('.font1').hide();
	$("#emapPopup").find("li").find('.font2').hide();
	$("#emapPopup").find(".selectedaddr").click(
			function() {
				var ind = Number($(this).attr("ind"));
				var item = poiArr[ind];
				currentInd = ind;
				currentObject = $(this);
				currentItem = item;
				var lnglat = item.location;
				var geoAddr = $.trim(autonavi_yum.cityName) + '市' + item.name;
				if (!lnglat.getLng() || !lnglat.getLat()) {
					lnglat = regeocode(geoAddr);
				}

				isRange(lnglat.getLng(), lnglat.getLat(), isRangeSuccessFun,
						isRangeErrorFun);

			});
	$("#emapPopup").find(".comfirmn").click(
			function() {
				var selectedAddress = currentItem.name;
				resultAddress = currentItem.name;
				if (currentItem.address != "" && currentItem.address != currentItem.name) {
					resultAddress = currentItem.name + "(" + currentItem.address + ")";
				}
				base.yumConfirm('你确定选择[' + selectedAddress + ']该地址?',
						function() {
							confirmAddress(currentItem.location.getLng(), currentItem.location.getLat(), currentItem.cityname, currentItem.name,currentItem.address, currentItem.adcode, storeCode, poddingId, poddingName, dayName, nightName,storeCode2, poddingId2, poddingName2,dayName2, nightName2, confirmAddressSuccessFun, confirmAddressErrorFun);
						}, function() {});
			});

}

function isRange(x, y, successFun, errorFun) {
	$.ajax({
		dataType : "jsonp",
		type : "GET",
		data : {
			sid : 200002,
			x : x,
			y : y,
			brand : autonavi_yum.brands,
			encode : autonavi_yum.charencode
		},
		url : EMap,
		success : function(data, textStatus) {
			successFun(data);
		},
		error : function() {
			errorFun();
		}
	});
}

function isRangeSuccessFun(data) {
	if (data == null || data == "") {
		return;
	}
	changeFont(currentObject);
	if (data.data.status == -1) {
		showComfirm(currentObject.parent().find('.font2'));
	} else {
		storeCode = data.data.storeCode;
		poddingId = data.data.poddingId;
		poddingName = data.data.poddingName;
		dayName = data.data.dayName;
		nightName = data.data.nightName;

		storeCode2 = data.data.storeCode2;
		poddingId2 = data.data.poddingId2;
		poddingName2 = data.data.poddingName2;
		dayName2 = data.data.dayName2;
		nightName2 = data.data.nightName2;

		showComfirm(currentObject.parent().find('.font1'));
	}
	var ind = Number(currentObject.attr("ind"));
	showAddressInMap(currentItem);
}

function isRangeErrorFun() {
	base.yumAlert(property.BadRequest);
}

function confirmAddress(x, y, cityName, name, address, postcode, storeCode, poddingId, poddingName, dayName, nightName, storeCode2, poddingId2,
		poddingName2, dayName2, nightName2, successFun, errorFun){
	window._tag && window._tag.dcsMultiTrack('wt.event', 'emap地址搜索', 'wt.msg', '查看更多确认');
	$.ajax({
		dataType : "jsonp",
		type : "GET",
		data : {
			sid : 1012,
			address : resultAddress,
			brands : getBrandName(autonavi_yum.brands),
			cityName : autonavi_yum.cityName,
			encode : autonavi_yum.charencode
		},
		url : autonavi_yum.hostAddr,
		success : function(data, textStatus) {
			if (data == null || data == "") {
				return;
			}
			var status = data.status;
			if (status && status.code != '0') {
				return;
			}
			if (data.data) {
				if (data.data.length == 0) {
					$.ajax({
						dataType : "jsonp",
						type : "GET",
						data : {
							sid : 200003,
							x : x,
							y : y,
							brand : autonavi_yum.brands,
							cityName : cityName,
							name : name,
							address : address,
							postcode : postcode,
							storeCode : storeCode,
							poddingId : poddingId,
							poddingName : poddingName,
							dayName : dayName,
							nightName : nightName,
							storeCode2 : storeCode2,
							poddingId2 : poddingId2,
							poddingName2 : poddingName2,
							dayName2 : dayName2,
							nightName2 : nightName2,
							encode : autonavi_yum.charencode
						},
						url : EMap,
						success : function(data, textStatus) {
							successFun();
						},
						error : function() {
							errorFun();
						}
					});
				} else {
					successFun();
				}
			}
		},
		error : function() {
			errorFun();
		}
	});
}

function confirmAddressSuccessFun() {
	$('#' + autonavi_yum.parentAddr).val(resultAddress);
	$("#emapPopup").dialog("close");
	if (autonavi_yum.callFun) {
		autonavi_yum.callFun(currentItem);
	}
}
function confirmAddressErrorFun() {
	base.yumAlert(property.BadRequest);
}
function regeocode(geoAddr) {
	var MGeocoder;
	// 加载地理编码插件
	AMap.service([ "AMap.Geocoder" ], function() {
		MGeocoder = new AMap.Geocoder({});
		// 返回地理编码结果
		// 地理编码
		MGeocoder.getLocation(geoAddr, function(status, result) {
			if (status === 'complete' && result.info === 'OK') {
				// 地理编码结果数组
				var geocode = new Array();
				geocode = data.geocodes;
				return new AMap.LngLat(geocode[0].location.getLng(),
						geocode[0].location.getLat());
			}
		});
	});
}

var preChangeElement = null, preComfirmAddrElement = null;
function changeFont(selectedElement) {
	if (preChangeElement) {
		preChangeElement.css("font-weight", "normal");
	}
	selectedElement.css("font-weight", "bold");
	preChangeElement = selectedElement;
}

function showComfirm(comfirmAddrElement) {
	if (!comfirmAddrElement) {
		return;
	}
	if (preComfirmAddrElement) {
		preComfirmAddrElement.hide();
	}
	comfirmAddrElement.show();
	preComfirmAddrElement = comfirmAddrElement;
}

function showAddressInMap(item) {
	var geoAddr = $.trim(autonavi_yum.cityName) + '市' + item.name;
	showAddressInMapCommon(item,geoAddr);
}

function showAddressInMapCommon(item,geoAddr) {
	if (item.location.getLng() && item.location.getLat()) {
		drawMarker(new AMap.LngLat(item.location.getLng(), item.location
				.getLat()));
	} else {
		regeocodeRoad(geoAddr);
	}
}

function regeocodeRoad(geoAddr) {
	var MGeocoder;
	// 加载地理编码插件
	AMap.service([ "AMap.Geocoder" ], function() {
		MGeocoder = new AMap.Geocoder({});
		// 返回地理编码结果
		// 地理编码
		MGeocoder.getLocation(geoAddr, function(status, result) {
			if (status === 'complete' && result.info === 'OK') {
				// 地理编码结果数组
				var geocode = new Array();
				geocode = data.geocodes;
				var centPoint = new AMap.LngLat(geocode[0].location.getLng(),
						geocode[0].location.getLat());
				drawMarker(centPoint);
			}
		});
	});
}

var mapObj;

function zoomIn() {
	if (mapObj) {
		mapObj.zoomIn();
	}

}

function zoomOut() {
	if (mapObj) {
		mapObj.zoomOut();
	}

}

function mapInit(resPath) {
	mapInitCommon("addressMap", resPath + '/res/images/imgjump.png');

}

function mapInitCommon(mapId, iconUrl, dragEnable) {
	if(dragEnable == "") {
		dragEnable = false;
	}

	if (!mapObj) {
		var opt = {
			zooms : [ 10, 18 ],
			doubleClickZoom : true,
			scrollWheel : true,
			dragEnable: dragEnable,
			// 二维地图显示视口
			// 设定地图中心点
			// 设置地图显示的缩放级别
			view : new AMap.View2D({
				center : new AMap.LngLat(121.44996739, 31.18478626),
				zoom : 16
			})
		};

		mapObj = new AMap.Map(mapId, opt);

		var fangda = document.getElementById('zoomin');
		var suoxiao = document.getElementById('zoomout');
		if(fangda != undefined) {
			fangda.onclick = zoomIn;
			suoxiao.onclick = zoomOut;
		} else {
			fangda = null;
			suoxiao = null;
		}

		curMarker = new AMap.Marker({
			map : mapObj,
			draggable : false,
			visible : false,
			icon : iconUrl,
			offset : new AMap.Pixel(-12, -25)
		});
	}

}
var curMarker = null;
function drawMarker(lnglat) {
	if (mapObj) {
		curMarker.setPosition(lnglat);
		curMarker.show();
		mapObj.setZoomAndCenter(18, lnglat);
		
		if ($.browser != undefined && $.browser.msie && $.browser.version.indexOf("8.0") > -1) {
			var pos = $("#emapPopup").offset();

			mapObj.panBy(-(pos.left + 100), -(pos.top + 50));
		}

	}
}
function getBrandName(brandId){
	if(brandId == "1"){
		return "KFC";
	} else if(brandId == "2"){
		return "PHHS";
	} else if(brandId == "3"){
		return "ED";
	}
}