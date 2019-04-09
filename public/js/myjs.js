function filterCodigo (phrase, _id, cellNr){  
    var suche = phrase.value.toLowerCase();
    var table = document.getElementById(_id);
    var ele;
    for (var r = 1; r < table.rows.length; r++){
        ele = table.rows[r].cells[cellNr].innerHTML.replace(/<[^>]+>/g,"");
        if (ele.toLowerCase().indexOf(suche)>=0 ) table.rows[r].style.display = '';
        else table.rows[r].style.display = 'none';
    }
}

function filterData (idDataI,idDataF, _id, cellNr){ 

    var DataITxt = document.getElementById(idDataI).value;
    var DataFTxt = document.getElementById(idDataF).value;

    var partesI = DataITxt.split("-");
    var dataI = new Date(partesI[0],partesI[1] - 1,partesI[2]);

    var partesF = DataFTxt.split("-");
    var dataF = new Date(partesF[0],partesF[1] - 1,partesF[2]);

    var table = document.getElementById(_id);
    var ele;

    for (var r = 1; r < table.rows.length; r++){
        
        ele = table.rows[r].cells[cellNr].innerHTML.replace(/<[^>]+>/g,"");
        
        partes = ele.split('/');
        var dataEle = new Date(partes[2],partes[1] - 1,partes[0]);
        
        if ((dataEle >= dataI) && (dataEle <= dataF))
          table.rows[r].style.display = '';
        else 
          table.rows[r].style.display = 'none';
    }
}

function getXmlHttp(){

    var xmlhttp;
    try{
        xmlhttp = new XMLHttpRequest();
    }catch(ee){
        try{
            xmlhttp = new ActiveXObject("Msxm12.XMLHTTP");
        }catch(e){
            try{
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }catch(E){
                xmlhttp = false;
            }
        }
    }
    return xmlhttp;
}

function getExtrato(){
    

    var http = getXmlHttp();
    var url = "/extrato";
    http.open("POST",url,true);

    http.onreadystatechange =  function () {
        if(http.readyState == 4 && http.status == 200){
            console.log("success");
        }
    };

    http.onload = function () {
        document.getElementById("extrato-div").innerHTML = this.responseText;
    };

    http.send();

}

function getTransacoes(){
    var numArquivo = document.getElementById("valueArq").innerHTML;

    var http = getXmlHttp();
    var url = "/transacao";
    http.open("POST",url,true);

    http.onreadystatechange =  function () {
        if(http.readyState == 4 && http.status == 200){
            console.log("success");
        }
    };

    http.onload = function () {
        document.getElementById("transacao-tbody").innerHTML = this.responseText;
    };

    http.send();

}
