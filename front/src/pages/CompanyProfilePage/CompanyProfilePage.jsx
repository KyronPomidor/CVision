import styles from "./CompanyProfilePage.module.css"

function CompanyProfilePage() {
    return (
      <div className={styles.CompanyProfilePage}>
        <div>
            <CompanyHeaderCard />
            <AboutCard />
            <GalleryCard />
        </div>
        <div>
            <CompanyDetailsCard />
            <JobOffersCard />
        </div>
      </div>
    )
  }
  
  export default CompanyProfilePage