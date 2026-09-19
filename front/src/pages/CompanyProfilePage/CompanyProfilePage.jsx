import styles from "./CompanyProfilePage.module.css";
import CompanyHeaderCard from "./components/CompanyHeaderCard/CompanyHeaderCard";
import AboutCard from "./components/AboutCard/AboutCard";
import CompanyDetailsCard from "./components/CompanyDetailsCard/CompanyDetailsCard";
import JobOffersCard from "./components/JobOffersCard/JobOffersCard";
import GalleryCard from "./components/GalleryCard/GalleryCard";

function CompanyProfilePage() {
  return (
    <div className={styles.CompanyProfilePage}>
      <div className={styles.column}>
          <CompanyHeaderCard/>
          <AboutCard/>
          <GalleryCard/>
      </div>
      <div className={styles.column}>
          <CompanyDetailsCard 
            socials={{
              linkedin: "https://linkedin.com/company/roslin",
              instagram: "https://instagram.com/roslin",
              twitter: "https://twitter.com",
              facebook: "https://facebook.com"
              }}
          />
          <JobOffersCard/>
      </div>
    </div>
  )
}
  
export default CompanyProfilePage