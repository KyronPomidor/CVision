import { useEffect, useState } from "react";

import styles from "./HomePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import RecommandCard from "./components/RecommandCard/RecommandCard";
import FilterCard from "./components/FilterCard/FilterCard";
import endavaLogo from "../../assets/endava-logo.png";
import pentalogLogo from "../../assets/pentalog-logo.png";
import roslinLogo from "../../assets/roslin-logo.png";
import profilePhoto from "../../assets/profile-photo.png";

// TODO: replace with getProfileByUser(userId) from "../../api/profiles"
async function getProfileByUser(userId) {
  const mockProfile = {
    cvId: 1,
    firstName: "Nicholas",
    lastName: "Carnegie",
    photo: profilePhoto,
    email: "nick.carn@gmail.com",
    phone: "+373 62 123 456",
    location: "Chișinău, Moldova",
    aboutMe: `A junior IT professional passionate about technology and problem-solving. 
I am eager to grow my skills, learn from experienced teams, and contribute to meaningful projects.`,
    // arrays of ids rather than plain counts:
    appliedJobs: [],
    savedJobs: [],
    cvViews: [1],
    reachouts: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
  };

  await new Promise((resolve) => setTimeout(resolve, 600));
  return mockProfile;
}

// TODO: replace with getRecommendations(userId) from "../../api/recommendations"
async function getRecommendations(userId) {
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
  const userId = 1;

  const [profile, setProfile] = useState(null);
  const [jobs, setJobs] = useState([]);
  const [savedJobs, setSavedJobs] = useState([]);
  const [appliedJobs, setAppliedJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    Promise.all([getProfileByUser(userId), getRecommendations(userId)])
      .then(([profileData, jobsData]) => {
        setProfile(profileData);
        setJobs(jobsData);
        setSavedJobs(profileData.savedJobs);
        setAppliedJobs(profileData.appliedJobs);
      })
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, [userId]);

  function handleToggleStar(jobId) {
    setSavedJobs((prev) =>
      prev.includes(jobId) ? prev.filter((id) => id !== jobId) : [...prev, jobId]
    );
    // TODO: persist via API, e.g. toggleSavedJob(userId, jobId)
  }

  function handleApply(jobId) {
    setAppliedJobs((prev) => (prev.includes(jobId) ? prev : [...prev, jobId]));
    // TODO: persist via API, e.g. applyToJob(userId, jobId)
  }

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Something went wrong.</p>;

  return (
    <div className={styles.HomePage}>
      <div>
        <ProfileCard
          variant="home"
          name={`${profile.firstName} ${profile.lastName}`}
          photo={profile.photo}
          email={profile.email}
          phone={profile.phone}
          location={profile.location}
          aboutMe={profile.aboutMe}
          appliedJobs={appliedJobs}
          savedJobs={savedJobs}
          cvViews={profile.cvViews}
          reachouts={profile.reachouts}
          onEditProfile={() => console.log("edit clicked")}
        />
      </div>

      <RecommandCard
        jobs={jobs}
        savedJobs={savedJobs}
        appliedJobs={appliedJobs}
        onToggleStar={handleToggleStar}
        onApply={handleApply}
      />

      <FilterCard />
    </div>
  );
}

export default HomePage;