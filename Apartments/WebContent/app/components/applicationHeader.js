Vue.component("application-header", {
	template: ` 
<div>
	<nav>
        <ul>
            <li><router-link to="/" exact>Apartments</router-link></li>
            <li><router-link to="/rc" exact>Reservation cart</router-link></li>
        </ul>
    </nav>

</div>		  
`

});