tdEvent();
changeTabs();

function tdEvent(){
    const tds = document.querySelectorAll('td');
    const $modal_name = document.getElementById('modal-name');

    tds.forEach((td, idx) => {
        td.addEventListener('click', ()=>{
            const month = td.getAttribute('data-month');
            const day = td.getAttribute('data-day');
            $modal_name.innerHTML = `${month}월 ${day}일 일정`;
        });
    });
}

/**
 * function
 */
function changeTabs() {
    const tabItems = document.querySelectorAll('.schedule_tabs');
    const tabContents = document.querySelectorAll('.schedule_tab_contents');

    tabItems.forEach((item, idx)=> {
        item.addEventListener('click', (e) => {
            tabContents.forEach(content => {
                content.style.display = 'none';
            })
            tabItems.forEach((content) => {
                content.classList.remove('active');
            })
            tabItems[idx].classList.add('active');
            tabContents[idx].style.display = 'block';
        });
    });
}

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

