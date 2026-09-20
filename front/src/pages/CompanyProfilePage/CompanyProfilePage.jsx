import styles from "./CompanyProfilePage.module.css";
import CompanyHeaderCard from "./components/CompanyHeaderCard/CompanyHeaderCard";
import AboutCard from "./components/AboutCard/AboutCard";
import CompanyDetailsCard from "./components/CompanyDetailsCard/CompanyDetailsCard";
import JobOffersCard from "./components/JobOffersCard/JobOffersCard";
import GalleryCard from "./components/GalleryCard/GalleryCard";

import logo from "./assets/roslinLogo.webp";

import img1 from "./assets/gallery/image1.jpg";
import img2 from "./assets/gallery/image2.jpg";
import img3 from "./assets/gallery/image3.jpg";
import img4 from "./assets/gallery/image4.jpg";

const mockCompany  = {
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
  ],
  details: {
    website: "www.roslin.us",
    email: "hr@roslin.us",
    address: "182 Stefan cel Mare Street, Chisinau, Moldova",
    size: 500,
    founded: 2019,
    socials:{
      linkedin: "https://linkedin.com/company/roslin",
      instagram: "https://instagram.com/roslin",
      twitter: "https://twitter.com",
      facebook: "https://facebook.com"
    }
  },
  jobs: [
    { id: 1, title: "Frontend Developer", schedule: "Flexible", salary: "$4,373 per year", experience: "No experience" },
    { id: 2, title: "Frontend Developer", schedule: "Flexible", salary: "$4,373 per year", experience: "No experience" },
    { id: 3, title: "Frontend Developer", schedule: "Flexible", salary: "$4,373 per year", experience: "No experience" },
  ]
};

function CompanyProfilePage({ company = mockCompany }) {
  return (
    <div className={styles.CompanyProfilePage}>
      <div className={styles.column}>
          <CompanyHeaderCard
              name={company.name}
              logo={company.logo}
              category={company.category}
          />
          <AboutCard
              name={company.name}
              description={company.description}
              perks={company.perks}
          />
          <GalleryCard
              gallery={company.gallery}
          />
      </div>
      <div className={styles.column}>
          <CompanyDetailsCard 
              details={company.details}
          />
          <JobOffersCard
              name={company.name}
              logo={company.logo}
              jobs={company.jobs}
          />
      </div>
    </div>
  )
}
  
export default CompanyProfilePage