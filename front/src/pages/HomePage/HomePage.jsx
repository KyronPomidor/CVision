import { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";

import styles from "./HomePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import RecommandCard from "./components/RecommandCard/RecommandCard";
import FilterCard from "./components/FilterCard/FilterCard";
import endavaLogo from "../../assets/endava-logo.png";
import pentalogLogo from "../../assets/pentalog-logo.png";
import roslinLogo from "../../assets/roslin-logo.png";

// TODO: replace with getRecommendations(userId) from "../../api/recommendations"
async function getRecommendations() {
  const mockJobs = [
    {
      id: 1,
      logo: endavaLogo,
      title: "Java Junior Developer",
      company: "Endava",
      postedAt: "2026-09-24T14:18:05.055Z",
      employmentType: "Full-time",
      salary: "5,200 per year",
      experience: "6-7 years of experience",
      matchPercent: 95,
      isStarred: false,
    },
    {
      id: 2,
      logo: pentalogLogo,
      title: "C# Junior Developer",
      company: "Pentalog",
      postedAt: "2026-09-24T14:18:05.055Z",
      employmentType: "Full-time",
      salary: "5,200 per year",
      experience: "6-7 years of experience",
      matchPercent: 60,
      isStarred: true,
    },
    {
      id: 3,
      logo: roslinLogo,
      title: "Middle Golang Engineer",
      company: "Roslin Solutions",
      postedAt: "2026-09-24T14:18:05.055Z",
      employmentType: "Full-time",
      salary: "5,200 per year",
      experience: "6-7 years of experience",
      matchPercent: 15,
      isStarred: true,
    },
    {
      id: 4,
      logo: roslinLogo,
      title: "Middle Golang Engineer",
      company: "Roslin Solutions",
      postedAt: "2026-09-24T14:18:05.055Z",
      employmentType: "Full-time",
      salary: "5,200 per year",
      experience: "6-7 years of experience",
      matchPercent: 15,
      isStarred: false,
    },
    {
      id: 5,
      logo: roslinLogo,
      title: "Middle Golang Engineer",
      company: "Roslin Solutions",
      postedAt: "2026-09-24T14:18:05.055Z",
      employmentType: "Full-time",
      salary: "5,200 per year",
      experience: "6-7 years of experience",
      matchPercent: 15,
      isStarred: false,
    },
    {
      id: 6,
      logo: roslinLogo,
      title: "Middle Golang Engineer",
      company: "Roslin Solutions",
      postedAt: "2026-09-24T14:18:05.055Z",
      employmentType: "Full-time",
      salary: "5,200 per year",
      experience: "6-7 years of experience",
      matchPercent: 15,
      isStarred: false,
    },
  ];

  await new Promise((resolve) => setTimeout(resolve, 1000));
  return mockJobs;
}

function HomePage() {
  const { profile, profileError } = useOutletContext();
  const [jobs, setJobs] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    getRecommendations().then(setJobs).catch(setError);
  }, []);

  if (profileError || error) return <p>Something went wrong.</p>;
  if (!jobs || !profile) return <p>Loading...</p>;

  return (
    <div className={styles.HomePage}>
      <div>
        <ProfileCard
          variant="home"
          name={profile.name}
          email={profile.email}
          phone={profile.phone}
          location={profile.location}
          aboutMe={profile.description}
          onEditProfile={() => console.log("edit clicked")}
        />
      </div>

      <RecommandCard jobs={jobs} />

      <FilterCard />
    </div>
  );
}

export default HomePage;
