import useTrendingMovies from "../hooks/useTrendingMovies";
import { useState } from "react";
import type { MovieSummary } from "../lib/types";
import SkeletonGrid from "../components/SkeletonGrid";
import { useFavorites } from "../hooks/useFavorites";
import MovieCard from "../components/MovieCard";

// MESSY NEEDS CLEANING UP 
export default function HomePage() {

  const [win, setWin] = useState<'day'|'week'>('day');
  const { has, toggle } = useFavorites();
  const { trendingMovies, error, loading, loadTrendingMovies } = useTrendingMovies(win);

  const hasData = Array.isArray(trendingMovies) && trendingMovies.length > 0;

  return (
    <div className="space-y-4">
      <div className="flex items-center gap-2">
        <button
          type="button"
          className={`px-3 py-2 rounded-xl text-sm ${win === "day" ? "bg-white text-black border-2" : "bg-black hover:cursor-pointer hover:border-2 hover:border-white"}`}
          onClick={() => setWin("day")}
        >
          Today
        </button>
        <button
          type="button"
          className={`px-3 py-2 rounded-xl text-sm ${win === "week" ? "bg-white text-black border-2" : "bg-black hover:cursor-pointer hover:border-2 hover:border-white"}`}
          onClick={() => setWin("week")}
        >
          This Week
        </button>
      </div>

      {loading && <SkeletonGrid />}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 p-3 rounded-2xl flex items-center justify-between">
          <span>{error}</span>
          <button
            type="button"
            onClick={loadTrendingMovies}
            className="px-3 py-2 rounded-xl text-sm bg-transparent hover:bg-red-100 focus-visible:outline-2 focus-visible:outline-red-600"
          >
            Retry
          </button>
        </div>
      )}

      {!loading && !error && !hasData && (
        <div className="text-sm text-gray-600">No trending movies right now.</div>
      )}

      {!loading && !error && hasData && (
        <div className="grid grid-cols-2 gap-4">
          {trendingMovies!.map((m: MovieSummary) => (
            <MovieCard
              key={m.id}
              movie={m}
              isFavorite={has(m.id)}
              onToggleFavorite={toggle}
            />
          ))}
        </div>
      )}
    </div>
  );
}