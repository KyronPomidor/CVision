import { Link } from "react-router-dom"
import mainLogo from "../../assets/main-logo.svg"
import styles from "./styles/LegalLayout.module.css"

function LegalLayout({ title, updatedAt, children }) {
    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <Link to="/" className={styles.logo}>
                    <img src={mainLogo} alt="CVision" />
                </Link>
            </header>

            <main className={styles.content}>
                <h1 className={styles.title}>{title}</h1>
                <p className={styles.updatedAt}>Last updated: {updatedAt}</p>

                {children}

                <Link to="/" className={styles.backLink}>
                    &larr; Back to CVision
                </Link>
            </main>
        </div>
    )
}

export default LegalLayout
