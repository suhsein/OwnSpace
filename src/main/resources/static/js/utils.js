// element 내용 복사
// function copy(str, msg) {
//     window.navigator.clipboard.writeText(str).then(() => {
//         alert(msg + checkKorean(msg) + " 복사되었습니다.");
//     });
// }

function copy(str, msg) {
    // clipboard API 사용
    if (navigator.clipboard !== undefined) {
        navigator.clipboard
            .writeText(str)
            .then(() => {
                alert(msg + checkKorean(msg) + ' 복사되었습니다.');
            });
    } else {
        // execCommand 사용
        const textArea = document.createElement('textarea');
        textArea.value = str;
        document.body.appendChild(textArea);
        textArea.select();
        textArea.setSelectionRange(0, 99999);
        try {
            document.execCommand('copy');
        } catch (err) {
            console.error('복사 실패', err);
        }
        textArea.setSelectionRange(0, 0);
        document.body.removeChild(textArea);
        alert(msg + checkKorean(msg) + ' 복사되었습니다.');
    }
};

function checkKorean(str) {
    const lastChar = str.charCodeAt(str.length - 1)
    const isThereLastChar = (lastChar - 0xac00) % 28
    if (isThereLastChar) {
        return '이'
    }
    return '가'
}
