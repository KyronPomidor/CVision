import styles from "./GalleryCard.module.css";
import caretLeft from "./assets/caret_circle_left.svg"
import caretRight from "./assets/caret_circle_right.svg"

function GalleryCard({ gallery }) {
    return (
        <div className={`${styles.card} ${styles.galleryCard}`}>
            <div className={styles.header}>
                <h2 className={styles.title}>Company Gallery</h2>
                <div className={styles.arrows}>
                    <button type="button" className={styles.arrowButton} aria-label="Previous" aria-label="Previous">
                        <img src={caretLeft} alt="" className={styles.arrowIcon} />
                    </button>
                    <button type="button" className={styles.arrowButton} aria-label="Next" aria-label="Next">
                        <img src={caretRight} alt="" className={styles.arrowIcon} />
                    </button>
                </div>
            </div>
            <div className={styles.grid}>
                {gallery.map((img) => 
                    <img key={img.src} src={img.src} alt={img.alt} />)
                }
            </div>
        </div>
    )
}

export default GalleryCard