function makeHighlight(keyword, $nodeList){
    for (const $node of $nodeList) {
        let innerHTML = $node.innerHTML;
        if(innerHTML.includes(keyword)){
            let idx = innerHTML.indexOf(keyword);
            innerHTML = innerHTML.substring(0, idx) + "<span class='highlight'>"
                + innerHTML.substring(idx, idx + keyword.length) + "</span>" + innerHTML.substring(idx + keyword.length);
            $node.innerHTML = innerHTML;
        }
    }
}
if(onSearch === true){
    window.onload = highlight(keyword);
}