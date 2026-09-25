import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import styles from "./CompanyProfilePage.module.css";
import CompanyHeaderCard from "./components/CompanyHeaderCard/CompanyHeaderCard";
import AboutCard from "./components/AboutCard/AboutCard";
import CompanyDetailsCard from "./components/CompanyDetailsCard/CompanyDetailsCard";
import JobOffersCard from "../../components/JobOffersCard/JobOffersCard";
import GalleryCard from "../../components/GalleryCard/GalleryCard";

import endavaLogo from "../../assets/endava-logo.png";
import pentalogLogo from "../../assets/pentalog-logo.png";
import roslinLogo from "../../assets/roslin-logo.png";

import img1 from "./assets/gallery/image1.jpg";
import img2 from "./assets/gallery/image2.jpg";
import img3 from "./assets/gallery/image3.jpg";
import img4 from "./assets/gallery/image4.jpg";
import img5 from "./assets/gallery/image5.png";

const defaultGallery = [
  { src: img1, alt: "Company logo on wall" },
  { src: img2, alt: "Notebook and flowers" },
  { src: img3, alt: "Team at a table" },
  { src: img4, alt: "Team relaxing on bean bags" },
  { src: img5, alt: "Team celebrating an anniversary" },
];

const mockCompanies = {
  1: {
    name: "Endava",
    logo: endavaLogo,
    category: "IT Company",
    description: `Endava is a global technology company helping businesses accelerate their digital transformation. We deliver end-to-end software engineering services for clients across finance, healthcare, and retail industries.
  We believe in fostering a culture of learning and collaboration, giving our engineers the tools and mentorship they need to grow their careers.`,
    perks: ["coffee", "growth", "flexible", "team"],
    gallery: defaultGallery,
    details: {
      website: "www.endava.com",
      email: "hr@endava.com",
      address: "1 Endava Street, Chisinau, Moldova",
      size: 12000,
      founded: 2000,
      socials: {
        linkedin: "https://linkedin.com/company/endava",
        instagram: "https://instagram.com/endava",
        twitter: "https://twitter.com",
        facebook: "https://facebook.com",
      },
    },
    jobs: [
      {
        id: 1,
        title: "Java Junior Developer",
        schedule: "Full-time",
        salary: "$5,200 per year",
        experience: "6-7 years of experience",
      },
      {
        id: 2,
        title: "Java Junior Developer",
        schedule: "Full-time",
        salary: "$5,200 per year",
        experience: "6-7 years of experience",
      },
      {
        id: 3,
        title: "Java Junior Developer",
        schedule: "Full-time",
        salary: "$5,200 per year",
        experience: "6-7 years of experience",
      },
    ],
  },

  2: {
    name: "Pentalog",
    logo: pentalogLogo,
    category: "IT Company",
    description: `Pentalog is an international digital services company that supports clients throughout their entire digital journey — from product strategy to engineering and operations.
  We build cross-functional teams that combine technical expertise with product thinking, working closely with clients across Europe and the US.`,
    perks: ["coffee", "growth", "flexible", "team"],
    gallery: defaultGallery,
    details: {
      website: "www.pentalog.com",
      email: "hr@pentalog.com",
      address: "44 Pentalog Avenue, Chisinau, Moldova",
      size: 1800,
      founded: 2005,
      socials: {
        linkedin: "https://linkedin.com/company/pentalog",
        instagram: "https://instagram.com/pentalog",
        twitter: "https://twitter.com",
        facebook: "https://facebook.com",
      },
    },
    jobs: [
      {
        id: 1,
        title: "C# Junior Developer",
        schedule: "Full-time",
        salary: "$5,200 per year",
        experience: "6-7 years of experience",
      },
      {
        id: 2,
        title: "C# Junior Developer",
        schedule: "Full-time",
        salary: "$5,200 per year",
        experience: "6-7 years of experience",
      },
      {
        id: 3,
        title: "C# Junior Developer",
        schedule: "Full-time",
        salary: "$5,200 per year",
        experience: "6-7 years of experience",
      },
    ],
  },

  3: {
    name: "Roslin Solutions",
    logo: roslinLogo,
    category: "IT Company",
    description: `Roslin Solutions is a forward-thinking technology company focused on building modern software solutions for global clients. We specialize in web and mobile application development, cloud solutions, and digital transformation services.
  Our mission is to empower businesses with innovative technology and talented people. We value creativity, collaboration, and continuous growth, and we’re always looking for passionate professionals to join our team.`,
    perks: ["coffee", "growth", "flexible", "team"],
    gallery: defaultGallery,
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
  },
};

// TODO: replace with getCompanyById(id) from "../../api/companies"
async function getCompany(id) {
  await new Promise((resolve) => setTimeout(resolve, 800));

  const company = mockCompanies[id];
  if (!company) {
    throw new Error(`Company with id ${id} not found`);
  }
  return company;
}

function CompanyProfilePage() {
  const params = useParams();
  const { companyId } = params;

  const [company, setCompany] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    getCompany(companyId)
      .then((data) => setCompany(data))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, [companyId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Something went wrong.</p>;

  return (
    <div className={styles.CompanyProfilePage}>
      <div className={styles.column}>
        <CompanyHeaderCard name={company.name} logo={company.logo} category={company.category} />
        <AboutCard name={company.name} description={company.description} perks={company.perks} />
        <GalleryCard gallery={company.gallery} />
      </div>
      <div className={styles.column}>
        <CompanyDetailsCard details={company.details} />
        <JobOffersCard name={company.name} logo={company.logo} jobs={company.jobs} />
      </div>
    </div>
  );
}

export default CompanyProfilePage;