import { useEffect } from "react";
import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import Header from "./components/Header/Header";
import HomePage from "./pages/HomePage/HomePage";
import ProfilePage from "./pages/ProfilePage/ProfilePage";
import CompanyProfilePage from "./pages/CompanyProfilePage/CompanyProfilePage";
import apiClient from "./api/client";

function MainLayout() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

function App() {
  // TODO: временный тест API — удалить после проверки
  useEffect(() => {
    apiClient
      .get("/skills")
      .then((res) => console.log("OK:", res.data))
      .catch((err) => console.error("FAIL:", err));
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        {/* Pages with Header */}
        <Route element={<MainLayout />}>
          <Route path="/" element={<HomePage />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/companies/:companyId" element={<CompanyProfilePage />} />
        </Route>

        {/* Pages without Header - add outside of MainLayout */}
        {/*Example: <Route path="/login" element={<LoginPage />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
