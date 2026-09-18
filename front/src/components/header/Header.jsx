import { Link } from "react-router-dom"
import styles from "./Header.module.css"
import Notifications from "../Notification/Notifications";
import mainLogo from "../../assets/main-logo.svg";
import searchLogo from "../../assets/search-icon.svg";
import settingsLogo from "../../assets/settings-icon.svg";
import notificationLogo from "../../assets/notification-icon.svg";
import profileLogo from "../../assets/profile-photo.png";

function Header() {

    return (
        <header className={styles.header} >
              
            <div className={styles.logoBox}> 
                <Link to="/">
                    <img src={mainLogo} alt="mainLogo" />
                </Link>
            </div>
            
            <div className={styles.searchBox}> 
                <form className={styles.searchBar} /* TODO: Back implementation*/>
                    <input 
                        type="text" 
                        className={styles.searchInput}
                        placeholder="Job title, Salary, or Companies...."
                        aria-label="Search jobs"
                        
                        //TODO: Back implementation
                    
                    />

                    <button type="submit" className={styles.searchButton} aria-label="Search button">
                        <img src={searchLogo} alt="searchLogo" className={styles.searchLogo}/>
                    </button>
                </form>
                

            </div>
            <div className={styles.toolsBox}>  
                <form action="" className={styles.toolsChoose}>
                    <button className={styles.setingsButton} aria-label="Settings button">
                        <img src={settingsLogo} alt="settingsLogo" className={styles.settingsLogo} />
                    </button>
                    <button className={styles.notificationButton} aria-label="Notification button">
                        <img src={notificationLogo} alt="notificationLogo" className={styles.notificationLogo} />
                    </button>
                    <Link to="/profile" className={styles.profileButton} aria-label="Profile button">
                        <img src={profileLogo} alt="profileLogo" className={styles.profileLogo} />
                    </Link>
                </form>
            </div>
            
            <Notifications></Notifications>
        </header>
    )
}

export default Header