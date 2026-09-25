import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import styles from "./JobPostingPage.module.css";
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

import endavaLogo from "../../assets/endava-logo.png";
import pentalogLogo from "../../assets/pentalog-logo.png";
import roslinLogo from "../../assets/roslin-logo.png";

import description from "./assets/yapping.txt?raw";

const defaultGallery = [
  { src: img1, alt: "Company logo on wall" },
  { src: img2, alt: "Notebook and flowers" },
  { src: img3, alt: "Team at a table" },
  { src: img4, alt: "Team relaxing on bean bags" },
  { src: img5, alt: "Team celebrating an anniversary" },
];

const defaultJobsList = [
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
];

const mockJobPostings = {
  1: {
    id: 1,
    title: "Java Junior Developer",
    description: description,
    skills: ["Java", "Spring", "Hibernate", "SQL", "REST", "Git"],
    details: {
      jobType: "Full-time",
      workSetting: "Hybrid",
      location: "Chisinau, Moldova",
      salary: 5200,
      schedule: "Full-time",
      experience: 6,
      education: "Does not matter",
      contactEmail: "hr@endava.com",
    },
    company: {
      id: 1,
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
      jobs: defaultJobsList,
    },
  },

  2: {
    id: 2,
    title: "C# Junior Developer",
    description: description,
    skills: ["C#", ".NET", "ASP.NET Core", "SQL Server", "Azure", "Git"],
    details: {
      jobType: "Full-time",
      workSetting: "Remote",
      location: "Chisinau, Moldova",
      salary: 5200,
      schedule: "Full-time",
      experience: 6,
      education: "Does not matter",
      contactEmail: "hr@pentalog.com",
    },
    company: {
      id: 2,
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
      jobs: defaultJobsList,
    },
  },

  3: {
    id: 3,
    title: "Middle Golang Engineer",
    description: description,
    skills: ["Golang", "Kubernetes", "Docker", "gRPC", "Kafka", "PostgreSQL"],
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
    company: {
      id: 3,
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
      jobs: defaultJobsList,
    },
  },
};

// TODO: replace with getJobPostingById(id) from "../../api/jobs"
async function getJobPosting(id) {
  await new Promise((resolve) => setTimeout(resolve, 800));

  const job = mockJobPostings[id];
  if (!job) {
    throw new Error(`Job with id ${id} not found`);
  }
  return job;
}

function JobPostingPage() {
  const params = useParams();
  const { jobId } = params;

  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    getJobPosting(jobId)
      .then((data) => setJob(data))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, [jobId]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Job not found</div>;

  return (
    <div className={styles.page}>
      <JobHeader
        className={`${styles.card} ${styles.jobHeader}`}
        logo={job.company.logo}
        jobName={job.title}
        companyName={job.company.name}
        companyId={job.company.id}
      />
      <JobDescription
        className={`${styles.card} ${styles.jobDescription}`}
        skills={job.skills}
        description={job.description}
      />
      <Sidebar className={`${styles.sidebar}`} data={job.details} perks={job.company.perks} />
      <GalleryCard className={styles.companyGallery} gallery={job.company.gallery} />
      <JobOffersCard
        className={`${styles.card} ${styles.jobOffers} `}
        name={job.company.name}
        logo={job.company.logo}
        jobs={job.company.jobs}
      />
    </div>
  );
}

export default JobPostingPage;