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

import { getCurrentUser } from "./api/users";
import { getCurrentProfile } from "./api/profiles";
import { getCurrentCompany } from "./api/companies";
import { optionalResource, errorMessage } from "./api/client";
import { mapProfile } from "./api/mappers";

function MainLayout() {
  const [account, setAccount] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    let active = true;
    async function loadAccount() {
      const user = await getCurrentUser();
      const resource = await optionalResource(
        user.role === "EMPLOYEE" ? getCurrentProfile() : getCurrentCompany(),
      );
      if (active)
        setAccount({
          user,
          profile: user.role === "EMPLOYEE" ? mapProfile(resource, user) : null,
          company: user.role === "EMPLOYER" ? resource : null,
        });
    }
    loadAccount().catch((err) => {
      if (active) setError(err);
    });
    return () => {
      active = false;
    };
  }, []);

  if (error) return <p role="alert">{errorMessage(error)}</p>;
  if (!account) return <p>Loading...</p>;
  return (
    <>
      <Header user={account.user} profile={account.profile} company={account.company} />
      <Outlet context={{ ...account, setAccount }} />
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
