// element 내용 복사
function copy(id, msg) {
    window.navigator.clipboard.writeText(id.textContent).then(() => {
        alert(msg + checkKorean(msg) + " 복사되었습니다.");
    });
}

function checkKorean(str) {
    const lastChar = str.charCodeAt(str.length - 1)
    const isThereLastChar = (lastChar - 0xac00) % 28
    if (isThereLastChar) {
        return '이'
    }
    return '가'
}
