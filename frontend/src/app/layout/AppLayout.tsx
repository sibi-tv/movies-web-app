import { Outlet, ScrollRestoration } from "react-router-dom";
import Navbar from "./Navbar";
import Hero from "./Hero";

export default function AppLayout() {
  return (
    <div className="min-h-screen bg-neutral-950 text-neutral-100">
      <Navbar />

      <Hero />

      <main className="mx-auto max-w-6xl px-4 py-8">
        <Outlet />
      </main>

      <ScrollRestoration />
    </div>
  );
}