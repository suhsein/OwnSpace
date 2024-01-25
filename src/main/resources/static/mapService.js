/**
 * @type {HTMLElement}
 */
const $map = document.getElementById('map');
const $mapSearchInput = document.getElementById("mapSearchInput");

let options = {
    center: new kakao.maps.LatLng(35.87492239563356, 127.13717269067321),
    level: 3
};

/**
 * @type {kakao.maps.Map}
 */
const map = new kakao.maps.Map($map, options);
const ps = new kakao.maps.services.Places();
const infowindow = new kakao.maps.InfoWindow({zIndex: 1});


/**
 * @type {*[]}
 */
let markers = [];


/**
 * eventListener
 */
$mapSearchInput.addEventListener("keyup", (e) => {
    if (e.key == 'Enter') {
        searchPlaces(e);
    }
})


/**
 * function
 */

// 키워드 검색을 요청하는 함수
function searchPlaces(e) {
    let keyword = $mapSearchInput.value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert("키워드를 입력해주세요");
        return false;
    }
    ps.keywordSearch(keyword, placeSearchCB);
}

// 검색 완료 시 호출되는 콜백함수
function placeSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        // 정상 처리
        // 장소 게시
        displayPlaces(data);
        // 페이지 번호 게시
        displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다');
        return;
    }
}

// 검색 결과 목록과 마커를 표출하는 함수
function displayPlaces(places) {
    const $placeList = document.getElementById("placesList"),
        $menuWrap = document.getElementById("menu_wrap"),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    // 검색 결과 목록 초기화
    removeAllChildNodes($placeList);

    // 지도의 마커 제거
    removeMarker();

    for (let i = 0; i < places.length; i++) {
        // 마커 생성 및 지도에 표시
        let coords = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(coords, i),
            itemEl = getListItem(i, places[i]);

        // 검색된 장소 위치 기준 지도 범위 재설정
        bounds.extend(coords);

        // 마커 혹은 검색 결과 목록 마우스 오버 시 이름 띄워주는 이벤트
        (function (marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function () {
                displayInfowindow(marker, title);
            })
            kakao.maps.event.addListener(marker, 'mouseout', function () {
                infowindow.close();
            })
            itemEl.onmouseover = function () {
                displayInfowindow(marker, title);
            };
            itemEl.onmouseout = function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    // 검색 결과 목록을 추가
    $placeList.appendChild(fragment);
    $menuWrap.scrollTop = 0;

    // 지도 범위 재설정
    map.setBounds(bounds);
}

// 검색 결과 목록을 Element로 반환하는 함수
function getListItem(index, places) {
    let $el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
            '<div class="info">' +
            '   <h5 onclick="copy(this, `장소명`)">' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span onclick="copy(this, `도로명 주소`)">' + places.road_address_name + '</span>' +
            '   <span class="jibun gray" onclick="copy(this, `주소`)">' + places.address_name + '</span>';
    } else {
        itemStr += '    <span onclick="copy(this, `주소`)">' + places.address_name + '</span>';
    }
    itemStr += '  <span class="tel" onclick="copy(this, `전화번호`)">' + places.phone + '</span>' + '</div>';

    $el.innerHTML = itemStr;
    $el.className = 'item';

    return $el;
}

// 마커를 생성, 지도 위에 마커 표시
function addMarker(position, idx, title) {
    const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
    let marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage
    });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 마커 모두 제거
function removeMarker() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호 표시
function displayPagination(pagination) {
    const $pagination = document.getElementById('pagination');
    let fragment = document.createDocumentFragment();

    // 기존에 추가된 페이지번호를 삭제합니다
    while ($pagination.hasChildNodes()) {
        $pagination.removeChild($pagination.lastChild);
    }

    for (let i = 1; i <= pagination.last; i++) {
        let $el = document.createElement('a');
        $el.href = "#";
        $el.innerHTML = i;

        if (i === pagination.current) {
            $el.className = 'on';
        } else {
            $el.onclick = (function (i) {
                return function () {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild($el);
    }
    $pagination.appendChild(fragment);
}

// 인포 윈도우에 장소명 표시
function displayInfowindow(marker, title) {
    let content = "<div style='padding:5px; z-index:1;'>" + title + "</div>";

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 검색 결과 목록 초기화
function removeAllChildNodes(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}

// element 내용 복사
function copy(id, msg) {
    window.navigator.clipboard.writeText(id.textContent).then(() => {
        alert(msg + " 복사완료");
    });
}

// menu visibility
function menuVisibility(id) {
    $menuWrap = document.getElementById("menu_wrap");
    if ($menuWrap.style.visibility !== 'hidden') {
        $menuWrap.style.visibility = 'hidden';
    } else {
        $menuWrap.style.visibility = 'visible';
    }
}