import { useEffect, useState } from "react";

import styles from "./ProfilePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import CVInformationCard from "./components/CVInformationCard/CVInformationCard";
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
    appliedJobs: [1, 2, 3],
    savedJobs: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
    cvViews: [1],
    reachouts: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
  };

  await new Promise((resolve) => setTimeout(resolve, 600));
  return mockProfile;
}

// TODO: replace with getCV(cvId) from "../../api/cv" — likely aggregates
async function getCV(cvId) {
  const mockCv = {
    technicalSkills: ["Microsoft Office", "Java", "Git", "Python", "C#", "php", "Golang"],
    languages: [
      { name: "English", level: "B2" },
      { name: "Romanian", level: "Native" },
      { name: "Russian", level: "C1" },
    ],
    objective:
      "Looking for a junior developer role where I can apply my problem-solving skills and grow within a collaborative engineering team, with a long-term interest in moving into full-stack development.",
    experience: [
      {
        id: 1,
        title: "IT Support Intern",
        company: "Endava",
        location: "Chișinău",
        startDate: "2026-09-24T14:18:05.055Z",
        endDate: "2026-09-24T14:18:05.055Z",
        description:
          'When creating a professional profile, blog or website, an "about me" statement can quickly show employers, clients or customers who you are and what you offer.',
      },
      {
        id: 2,
        title: "IT Support Intern",
        company: "Endava",
        location: "Chișinău",
        startDate: "2026-09-24T14:18:05.055Z",
        endDate: "2026-09-24T14:18:05.055Z",
        description:
          'When creating a professional profile, blog or website, an "about me" statement can quickly show employers, clients or customers who you are and what you offer.',
      },
      {
        id: 3,
        title: "IT Support Intern",
        company: "Endava",
        location: "Chișinău",
        startDate: "2026-09-24T14:18:05.055Z",
        endDate: "2026-09-24T14:18:05.055Z",
        description:
          'When creating a professional profile, blog or website, an "about me" statement can quickly show employers, clients or customers who you are and what you offer.',
      },
    ],
    additionalInfo:
      "When creating a professional profile, blog or website, an \u201cabout me\u201d statement can quickly show employers, clients or customers who you are and what you offer. ",
  };

  await new Promise((resolve) => setTimeout(resolve, 800));
  return mockCv;
}

function ProfilePage() {
  // TODO: pull the real userId from auth/session context once it exists
  const userId = 1;

  const [profile, setProfile] = useState(null);
  const [cv, setCv] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    getProfileByUser(userId)
      .then((profileData) => {
        setProfile(profileData);
        return getCV(profileData.cvId);
      })
      .then((cvData) => setCv(cvData))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, [userId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Something went wrong.</p>;

  return (
    <div className={styles.ProfilePage}>
      <ProfileCard
        variant="profile"
        name={`${profile.firstName} ${profile.lastName}`}
        photo={profile.photo}
        email={profile.email}
        phone={profile.phone}
        location={profile.location}
        aboutMe={profile.aboutMe}
        appliedJobs={profile.appliedJobs}
        savedJobs={profile.savedJobs}
        cvViews={profile.cvViews}
        reachouts={profile.reachouts}
        onEditProfile={() => console.log("edit clicked")}
        onUploadCV={() => console.log("upload cv clicked")}
      />

      <CVInformationCard
        technicalSkills={cv.technicalSkills}
        languages={cv.languages}
        objective={cv.objective}
        experience={cv.experience}
        additionalInfo={cv.additionalInfo}
      />
    </div>
  );
}

export default ProfilePage;
