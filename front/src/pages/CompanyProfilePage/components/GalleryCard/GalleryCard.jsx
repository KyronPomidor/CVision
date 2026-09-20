import styles from "./GalleryCard.module.css";

function GalleryCard({ gallery }) {
    return (
        <div className={`${styles.card} ${styles.galleryCard}`}>
            <div className={styles.header}>
                <h2 className={styles.title}>Company Gallery</h2>
                <div className={styles.arrows}>
                    <button aria-label="Previous">‹</button>
                    <button aria-label="Next">›</button>
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