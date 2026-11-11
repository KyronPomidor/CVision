import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import Header from "./components/Header/Header";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import HomePage from "./pages/HomePage/HomePage";
import ProfilePage from "./pages/ProfilePage/ProfilePage";
import CompanyProfilePage from "./pages/CompanyProfilePage/CompanyProfilePage";
import LoginPage from "./pages/LoginPage/LoginPage";
import SignUpPage from "./pages/SignUpPage/SignUpPage";
import JobPostingPage from "./pages/JobPostingPage/JobPostingPage";
import TwoFactorPage from "./pages/TwoFactorPage/TwoFactorPage";

import { getCurrentProfile } from "./api/profiles";

function MainLayout() {
  const [profile, setProfile] = useState(null);
  const [profileError, setProfileError] = useState(null);

  useEffect(() => {
    const controller = new AbortController();

    getCurrentProfile(controller.signal)
      .then(setProfile)
      .catch((err) => {
        if (!controller.signal.aborted) setProfileError(err);
      });

    return () => controller.abort();
  }, []);

  return (
    <>
      <Header name={profile?.name} />
      <Outlet context={{ profile, profileError }} />
    </>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<ProtectedRoute />}>
          <Route element={<MainLayout />}>
            <Route path="/" element={<HomePage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/companies/:companyId" element={<CompanyProfilePage />} />
            <Route path="/jobs/:jobId" element={<JobPostingPage />} />
          </Route>
        </Route>

        {/* Pages without Header - add outside of MainLayout */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/twofactor" element={<TwoFactorPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
