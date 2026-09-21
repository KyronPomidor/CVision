import { useState } from "react";
import styles from "./GalleryCard.module.css";
import caretLeft from "./assets/caret_circle_left.svg"
import caretRight from "./assets/caret_circle_right.svg"

const VISIBLE = 4;

function GalleryCard({ gallery }) {
    const [start, setStart] = useState(0);

    const total = gallery.length;
    const canScroll = total > VISIBLE;

    const next = () => setStart((s) => (s + 1) % total);
    const prev = () => setStart((s) => (s - 1 + total) % total);

    const visible = canScroll
        ? Array.from({ length: VISIBLE }, (_, i) => gallery[(start + i) % total])
        : gallery;

    return (
        <div className={`${styles.card} ${styles.galleryCard}`}>
            <div className={styles.header}>
                <h2 className={styles.title}>Company Gallery</h2>
                <div className={styles.arrows}>
                    <button type="button" className={styles.arrowButton} aria-label="Previous" onClick={prev}>
                        <img src={caretLeft} alt="" className={styles.arrowIcon} />
                    </button>
                    <button type="button" className={styles.arrowButton} aria-label="Next" onClick={next}>
                        <img src={caretRight} alt="" className={styles.arrowIcon} />
                    </button>
                </div>
            </div>
            <div className={styles.grid}>
                {visible.map((img) => 
                    <img key={img.src} src={img.src} alt={img.alt} />)
                }
            </div>
        </div>
    )
}

export default GalleryCard