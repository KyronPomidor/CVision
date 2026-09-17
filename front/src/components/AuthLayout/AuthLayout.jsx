import { Link } from "react-router-dom"
import mainLogo from "../../assets/main-logo.svg"
import worldMap from "../../assets/world-map.png"
import styles from "./styles/AuthLayout.module.css"

function AuthLayout({ children, mapOffset = -220 }) {
    return (
        <div className={styles.page}>
            <Link to="/" className={styles.logo}>
                <img src={mainLogo} alt="CVision" />
            </Link>

            <div className={styles.stack}>
                <div className={styles.content}>
                    {children}
                </div>

                <div
                    className={styles.mapWrapper}
                    style={{ marginTop: mapOffset }}
                    aria-hidden="true"
                >
                    <img src={worldMap} alt="" className={styles.mapImage} />
                </div>
            </div>

            <footer className={styles.footer}>
                <Link to="/privacy-policy">Privacy Policy</Link>
                <Link to="/terms-of-service">Terms of Service</Link>
            </footer>
        </div>
    )
}

export default AuthLayout
