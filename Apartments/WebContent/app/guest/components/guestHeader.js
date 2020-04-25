Vue.component("guest-header",{
    template: `
    <div>
        <nav>
            <ul>
                <li><router-link to="/home" exact>Home Page</router-link></li>
                <li><router-link to="/" exact>View apartments & rooms</router-link></li>
                <li><router-link to="/reservations" exact>Reservations</router-link></li>
                <li><router-link to="/profile" exact>Profile</router-link></li>
                <li><router-link to="/logout" exact>Log out</router-link></li>
            </ul>
        </nav>

    </div>		  
    
    `
});