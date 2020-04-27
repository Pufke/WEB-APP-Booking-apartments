Vue.component("application-header", {
	template: ` 
<div>
	<nav>
        <ul>
            <li><router-link to="/" exact>Home</router-link></li>
            <li><router-link to="/apartments" exact>Apartments</router-link></li>
            <li><router-link to="/reservation" exact>Reservation cart</router-link></li>
            <li><router-link to="/register" exact> Sign up </router-link></li>
            <li><router-link to="/login" exact> Log in </router-link></li>
        </ul>
    </nav>

</div>		  
`

});