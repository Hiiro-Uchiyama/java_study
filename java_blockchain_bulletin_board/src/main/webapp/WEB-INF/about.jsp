<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bulletin Board with Blockchain</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
</head>
<style>
html {
	position: relative;
	min-height: 100%;
}

body {
	margin-bottom: 60px;
}

.footer {
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 60px;
	background-color: #312d2a;
}

.container {
	width: auto;
	max-width: 680px;
	padding: 0 15px;
}

.container .text-muted {
	margin: 20px 0;
}
</style>
<body>
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark mb-3"> <a
		class="navbar-brand" href="<c:url value='/indexblock' />">Bulletin Board with Blockchain</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNav4" aria-controls="navbarNav4"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse justify-content-end"
		id="navbarNav4">
		<ul class="navbar-nav">
			<li class="nav-item active"><a class="nav-link" href="<c:url value='/index' />">Index</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/indexblock' />">BlockChain</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/specification' />">Specifications</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/usage' />">How to use</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/about' />">About Blockchain</a></li>
		</ul>
	</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-12 mt-5">
				<h3>About Blockchain</h3>
			</div>
			<div class="col-8 mt-3 mb-5">
				<p>当サイトは掲示板のデータベースにブロックチェーンと同様のアルゴリズムを用いたモノです。</p>
				<p>参考とさせていただいたサイト様と致しましては下記に参考文献としてご紹介させていただきます。</p>
				<p><a href="https://www.softbank.jp/biz/future_stride/entry/technology/20180425/">ブロックチェーンの基礎知識</a></p>
			    <p><a href="https://techblog.gmo-ap.jp/2018/12/26/ブロックチェーンを作ってみました%E3%80%82/">Javaブロックチェーンのソースコード</a></p>
			    <p><a href="https://techacademy.jp/magazine/22175">掲示板の参考ソース</a></p>
			    <p><a href="https://qiita.com/parapore/items/28ca111a5a3c0cbdcf0a">掲示板の参考ソース</a></p>
			    <p><a href="https://www.dbonline.jp/mysql/">MySQLの使い方</a></p>
			    <p>誠に勝手ながら参考にさせていただきました。</p>
			    <p>ありがとうございました。</p>
			</div>
			<div class="col-4 mt-3 mb-5">
				
			</div>
		</div>
	</div>
	<footer class="footer">
	<div class="container text-center">
		<p class="text-muted">©2022 kagoshima uv hiiro uchiyama</p>
	</div>
	</footer>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"
		integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em"
		crossorigin="anonymous"></script>
</body>
</html>