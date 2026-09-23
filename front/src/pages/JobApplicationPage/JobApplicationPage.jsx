import styles from "./JobApplicationPage.module.css";
import JobHeader from "./components/JobHeader/JobHeader";
import JobDescription from "./components/JobDescription/JobDescription";
import Sidebar from "./components/Sidebar/Sidebar";
import GalleryCard from "../CompanyProfilePage/components/GalleryCard/GalleryCard";
import JobOffersCard from "../CompanyProfilePage/components/JobOffersCard/JobOffersCard";

import img1 from "../CompanyProfilePage/assets/gallery/image1.jpg";
import img2 from "../CompanyProfilePage/assets/gallery/image2.jpg";
import img3 from "../CompanyProfilePage/assets/gallery/image3.jpg";
import img4 from "../CompanyProfilePage/assets/gallery/image4.jpg";
import img5 from "../CompanyProfilePage/assets/gallery/image5.png";

import logo from "../../assets/roslin-logo.png";

const gallery = [
      { src: img1, alt: "Roslin logo on wall" },
      { src: img2, alt: "Roslin notebook and flowers" },
      { src: img3, alt: "Team at a table" },
      { src: img4, alt: "Team relaxing on bean bags" },
      { src: img5, alt: "Team celebrating an anniversary" },
]

const name = "Roslin Solutions"

const jobs = [
    {
      id: 1,
      title: "Frontend Developer",
      schedule: "Flexible",
      salary: "$4,373 per year",
      experience: "No experience",
    },
    {
      id: 2,
      title: "Frontend Developer",
      schedule: "Flexible",
      salary: "$4,373 per year",
      experience: "No experience",
    },
    {
      id: 3,
      title: "Frontend Developer",
      schedule: "Flexible",
      salary: "$4,373 per year",
      experience: "No experience",
    },
]

function JobApplicationPage() {
    return (
        <div className={styles.page}>
            <JobHeader className={`${styles.card} ${styles.jobHeader}`} />
            <JobDescription className={`${styles.card} ${styles.jobDescription}`} />
            <Sidebar className={`${styles.sidebar}`} />
            <GalleryCard className={styles.companyGallery} gallery={gallery} />
            <JobOffersCard className={`${styles.card} ${styles.jobOffers} `} name={name} logo={logo} jobs={jobs} />
        </div>
    );
}

export default JobApplicationPage;
