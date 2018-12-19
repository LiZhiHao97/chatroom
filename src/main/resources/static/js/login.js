var panelBoxes = document.querySelectorAll('.panel--box'),
    signUp = document.getElementById('signUp'),
    signIn = document.getElementById('signIn');

function removeSelection(){
    for(var i = 0, len = panelBoxes.length; i < len; i++){
        panelBoxes[i].classList.remove('active');
    }
}

window.onpopstate = function(event) {

    if(event.state === 'signin') {
        signIn.click();
    }

    if(event.state === 'signup') {
        signUp.click();
    }

};


signIn.onclick = function(e){
    e.preventDefault();
    removeSelection();
    panelBoxes[0].classList.add('active');
}

signUp.onclick = function(e){
    e.preventDefault();
    removeSelection();
    panelBoxes[1].classList.add('active');
}

function loginCheck() {
    var usernameValue = document.getElementById("login-username").value;
    var passwordValue = document.getElementById("login-password").value;

    if(usernameValue.length < 6) {
        alert("用户名不得小于6位");
        return false;
    }
    if (passwordValue.length < 6) {
        alert("密码不得少于6位");
        return false;
    }
    window.location.href = "www.baidu.com";
    return true;
};

function registerCheck() {
    var usernameValue = document.getElementById("register-username").value;
    var passwordValue = document.getElementById("register-password").value;
    var checkPasswordValue = document.getElementById("register-check-password").value;

    if (usernameValue.length < 6) {
        alert("用户名不得小于6位");
        return false;
    }

    if (passwordValue.length < 6) {
        alert("密码不得小于6位");
        return false;
    }

    if (passwordValue !== checkPasswordValue) {
        alert("两次密码输入不相同");
        return false;
    }
    return true;
};