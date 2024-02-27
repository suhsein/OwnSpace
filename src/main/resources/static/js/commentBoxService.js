const $reply_btn_list = document.querySelectorAll('.reply_btn');
const $comment_box = document.getElementById('comment_box');

for (const $reply_btn of $reply_btn_list) {
    $reply_btn.addEventListener('click', () => {
        let reply_btn_id = $reply_btn.getAttribute('id');
        let idx = reply_btn_id.substring(reply_btn_id.lastIndexOf('_') + 1);
        let $reply_box = document.getElementById('reply_box_' + idx);
        if ($reply_btn.textContent === '↪답글') {
            $reply_btn.textContent = '취소';
            if ($comment_box != null) {
                $comment_box.style.display = 'none';
            }
            $reply_box.style.display = 'block';
        } else {
            $reply_btn.textContent = '↪답글';
            if ($comment_box != null) {
                $comment_box.style.display = 'block';
            }
            $reply_box.style.display = 'none';
        }
    });
}