const $pwButtons = document.querySelectorAll('.password');
for (const $pwButton of $pwButtons) {
    const id = $pwButton.getAttribute("id");
    const target_id = id.substring(0, id.lastIndexOf('_'));
    const $target = document.getElementById(target_id);
    $pwButton.addEventListener('click', () => {
        const type = $target.getAttribute("type");
        if (type === 'text'){
            $target.setAttribute("type", "password");
            $pwButton.firstElementChild.classList.replace("fa-eye-slash", "fa-eye");
        } else{
            $target.setAttribute("type", "text");
            $pwButton.firstElementChild.classList.replace("fa-eye", "fa-eye-slash");
        }
    });
}