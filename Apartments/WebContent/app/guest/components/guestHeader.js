Vue.component("guest-header",{
    template: `
    <div>
        <nav>
            <ul>
                <li><router-link to="/home" exact>Home</router-link></li>
                <li><router-link to="/" exact>Apartments</router-link></li>
                <li><router-link to="/rc" exact>Reservation cart</router-link></li>
                <li><router-link to="/logout" exact>Log out</router-link></li>
            </ul>
        </nav>

    </div>		  
    
    `
});