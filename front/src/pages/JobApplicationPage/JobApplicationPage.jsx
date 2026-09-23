import styles from "./JobApplicationPage.module.css";
import JobHeader from "./components/JobHeader/JobHeader";
import JobDescription from "./components/JobDescription/JobDescription";
import Sidebar from "./components/Sidebar/Sidebar";
import GalleryCard from "../../components/GalleryCard/GalleryCard";
import JobOffersCard from "../../components/JobOffersCard/JobOffersCard";

import img1 from "../CompanyProfilePage/assets/gallery/image1.jpg";
import img2 from "../CompanyProfilePage/assets/gallery/image2.jpg";
import img3 from "../CompanyProfilePage/assets/gallery/image3.jpg";
import img4 from "../CompanyProfilePage/assets/gallery/image4.jpg";
import img5 from "../CompanyProfilePage/assets/gallery/image5.png";

import logo from "../../assets/roslin-logo.png";

import description from "./assets/yapping.txt?raw";

const mockCompany = {
    name: "Roslin Solutions",
    logo: logo,
    category: "IT Company",
    description: `Roslin Solutions is a forward-thinking technology company focused on building modern software solutions for global clients. We specialize in web and mobile application development, cloud solutions, and digital transformation services.
  Our mission is to empower businesses with innovative technology and talented people. We value creativity, collaboration, and continuous growth, and we’re always looking for passionate professionals to join our team.`,

    perks: ["coffee", "growth", "flexible", "team"],
    gallery: [
      { src: img1, alt: "Roslin logo on wall" },
      { src: img2, alt: "Roslin notebook and flowers" },
      { src: img3, alt: "Team at a table" },
      { src: img4, alt: "Team relaxing on bean bags" },
      { src: img5, alt: "Team celebrating an anniversary" },
    ],
    details: {
      website: "www.roslin.us",
      email: "hr@roslin.us",
      address: "182 Stefan cel Mare Street, Chisinau, Moldova",
      size: 500,
      founded: 2019,
      socials: {
        linkedin: "https://linkedin.com/company/roslin",
        instagram: "https://instagram.com/roslin",
        twitter: "https://twitter.com",
        facebook: "https://facebook.com",
      },
    },
    jobs: [
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
    ],
  };

const mockJobApplication = {
  title: "Middle Golang Engineer",
  description: description,
  skills: [
      "Golang",
      "Kubernetes",
      "Docker",
      "gRPC",
      "Kafka",
      "PostgreSQL",
  ],
  details: {
      jobType: "Full-time Contract",
      workSetting: "Remote",
      location: "Chisinau, Moldova",
      salary: 5200,
      schedule: "Full-time",
      experience: 6,
      education: "Does not matter",
      contactEmail: "hr@roslin.us",
  },
};

function JobApplicationPage() {
    return (
        <div className={styles.page}>
            <JobHeader className={`${styles.card} ${styles.jobHeader}`} logo={logo} jobName={mockJobApplication.title} companyName={mockCompany.name} />
            <JobDescription className={`${styles.card} ${styles.jobDescription}`} skills={mockJobApplication.skills} description={description}/>
            <Sidebar className={`${styles.sidebar}`} data={mockJobApplication.details} perks={mockCompany.perks} />
            <GalleryCard className={styles.companyGallery} gallery={mockCompany.gallery} />
            <JobOffersCard className={`${styles.card} ${styles.jobOffers} `} name={mockCompany.name} logo={logo} jobs={mockCompany.jobs} />
        </div>
    );
}

export default JobApplicationPage;
