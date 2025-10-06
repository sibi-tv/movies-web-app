import type { MovieSummary } from "../../lib/types";

type cardProperties = {
  movie: MovieSummary;
  isFavorite: boolean;
  onToggleFavorite: (id: number) => void;
};

export type { cardProperties }