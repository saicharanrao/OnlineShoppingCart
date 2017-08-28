<p class="lead">Categories</p>
<div class="list-group">
 
 
 <!--  	<a href="#" class="list-group-item">Category 1</a> 
	<a href="#" class="list-group-item">Category 2</a> 
	<a href="#" class="list-group-item">Category 3</a> -->
	
	
	<c:forEach items = "${categories }" var = "category">
		<a href="${contextRoot }/show/category/${category.id}/products" class="list-group-item" id="a_${category.name }">${category.name }</a>
	</c:forEach>
</div>