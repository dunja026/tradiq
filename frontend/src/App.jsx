import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import TradingPage from "./features/trading/TradingPage";
import "./App.css";

function App() {
  return (
    <div>
      <h1>TradIQ</h1>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/trade" element={<TradingPage />} />
      </Routes>
    </div>
  );
}

export default App;
