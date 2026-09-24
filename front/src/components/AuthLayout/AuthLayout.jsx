import { Link } from "react-router-dom"
import MainLogo from "../../../../Logo.svg?react"
import worldMap from "../../assets/world-map.png"
import styles from "./AuthLayout.module.css"

function AuthLayout({ children, mapOffset = -220 }) {
    return (
        <div className={styles.page}>
            <Link to="/" className={styles.logoBox} aria-label="CVision">
                <MainLogo className={styles.logo}/>
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
        </div>
    )
}

export default AuthLayout