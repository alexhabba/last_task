const url = 'https://www.cbr-xml-daily.ru/daily_json.js'
const isAsinc = false;

function main() {
    const request = new XMLHttpRequest();
    request.open('GET', url, isAsinc);
    request.send();
    const data = JSON.parse(request.response);
    console.log(data)
}

function main2() {
    $.ajax(url).done(date => console.log(JSON.parse(date)));
}

function main3() {
    const request = fetch(url);
    request
        .then(response => response.json())
        .then(data => console.log(data));
}

function main5() {
    fetch(url)
        .then(response => response.json())
        .then(data => console.log(data));
}

async function main6() {
    const response = await fetch(url);
    const data = await response.json();
    console.log(data);
}

async function main7() {
    const response = await fetch(url);
    const data = await response.json();

    let valutes = data.Valute;
    console.log(valutes);

    let html ='';
    let count = 1;

    for (let key in valutes) {
        let val = valutes[key];
        html += '<tr>' + '<td>' + count + '</td>';
        for (let valueObject in val) {
            html += '<td>' + val[valueObject] + '</td>' + '\n';
        }
        html += '<td><button id="get/ ' + count++ +'" type="button" onclick="f()" >button</button><tr>'


    }
    $('#table').after(html)
    // console.log(html)
}

$('.btn').click(function (){
    $(this).css('color','red')
})

function f() {
    // this.text.css('color','red')
    // console.log(t)

    // for (ti in this){
    //     console.log(ti )
    // }
t = this
    // let outerHTML = t.outerHTML;
    // console.log(outerHTML)
    console.log(t.id)
    console.log(t.type)
    console.log(t.tagName)
    // alert(t)
}
function main8() {
    fetch('/', {
        method: 'POST', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({id: null, name: 'inna', lastName: 'Victorovna'})
    }).then(console.log)
}

main7();
