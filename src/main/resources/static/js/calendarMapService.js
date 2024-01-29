document.addEventListener('keydown', function (event) {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
    ;
}, true);

function openMapWrap(){
    const $map_wrap = document.querySelector('.map_wrap');
    const $openMap = document.getElementById('openMap');
    if ($map_wrap.style.display !== 'none') {
        $openMap.innerHTML = '지도 열기';
        $map_wrap.style.display = 'none';
    } else {
        $openMap.innerHTML = '지도 닫기';
        $map_wrap.style.display = 'block';
        generateMap();
    }
}

