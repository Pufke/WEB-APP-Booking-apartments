
function marg1(){
	var slider = document.querySelector('#slider');
	slider.style.marginLeft='-100%';
	function marg2(){
		var slider=document.querySelector('#slider');
	slider.style.marginLeft='-200%';
		function marg0(){
	var slider = document.querySelector('#slider');
	slider.style.marginLeft='0';
			setTimeout(marg1, 8000);
		}
		setTimeout(marg0, 8000);
	}
	setTimeout(marg2, 8000);
}

setInterval(slid, 7500);
setInterval(para, 7200);
setInterval(head, 7200);
setInterval(line, 7300);
setTimeout(marg1, 8000);
setInterval(slid2, 8500);
setInterval(head2, 8700);
setInterval(para2, 8700);
setInterval(line2, 8600);

function slid(){
	var s=document.querySelectorAll('.slid');
	var i;
	for(i=0; i<s.length; i++){
		s[i].style.transform='scale(.8)';
	}
}

function slid2(){
	var s=document.querySelectorAll('.slid');
	var i;
	for(i=0; i<s.length; i++){
		s[i].style.transform='scale(1)';
	}
}

function head(){
	var h=document.querySelectorAll('h1');
	var i;
	for(i = 0; i<h.length; i++){
		h[i].style.top='70px';
	}
}

function head2(){
	var h=document.querySelectorAll('h1');
	var i;
	for(i = 0; i<h.length; i++){
		h[i].style.top='0';
	}
}

function para(){
	var p = document.querySelectorAll('p');
	var i;
	for(i=0; i<p.length; i++){
		p[i].syle.bottom='40px';
	}
}
function para2(){
	var p = document.querySelectorAll('p');
	var i;
	for(i=0; i<p.length; i++){
		p[i].syle.bottom='0';
	}
}

function line(){
	var l = document.querySelector('.line');
	var i;
	for(i=0; i<l.length; i++){
		l[i].syle.transform='scale(0)';
	}
}

function line2(){
	var l = document.querySelector('.line');
	var i;
	for(i=0; i<l.length; i++){
		l[i].syle.transform='scale(1)';
	}
}