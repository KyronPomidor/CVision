import styles from "./GalleryCard.module.css";
import img1 from "./gallery/image1.jpg";
import img2 from "./gallery/image2.jpg";
import img3 from "./gallery/image3.jpg";
import img4 from "./gallery/image4.jpg";

const images = [
    { src: img1, alt: "Roslin logo on wall" },
    { src: img2, alt: "Roslin notebook and flowers" },
    { src: img3, alt: "Team at a table" },
    { src: img4, alt: "Team relaxing on bean bags" },
  ];

function GalleryCard() {
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
                {images.map((img) => 
                    <img key={img.src} src={img.src} alt={img.alt} />)
                }
            </div>
        </div>
    )
}

export default GalleryCard